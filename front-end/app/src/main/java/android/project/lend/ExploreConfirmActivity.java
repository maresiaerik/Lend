package android.project.lend;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExploreConfirmActivity extends AppCompatActivity {

    ProductCore itemData;
    ProductDataItem productDataItem;
    String startDate, endDate;
    String imageURL;

    Helper.LendzData newLenzDataItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_confirm);


        //Setting Cancel Button To Go Back
        Button cancelBtn = findViewById(R.id.confirm_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Checking For User + Setting Button Text + Listener
        Button borrowBtn = findViewById(R.id.confirm_borrow_btn);
        if (MainActivity.USER.getId()>0/*false*/) {
            borrowBtn.setText("Borrow");
            borrowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrow();
                }
            });
        }else{
            borrowBtn.setText("Login");
            borrowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

        }

        //Get Item Data From Intent
        Intent intent = getIntent();
        itemData = intent.getParcelableExtra("itemData");
        startDate = intent.getStringExtra("datesBtn");
        endDate = intent.getStringExtra("endDate");
        imageURL = intent.getStringExtra("ImageURL");

        if (itemData != null) {
            setItemData();
        }


    }

    //Start Login Activity
    private void login() {
        Intent loginIntent = new Intent(this, HomeUserLogin.class);
        startActivity(loginIntent);

    }

    //Setting Item Details From Intent Extras
    private void setItemData() {
        //Name
        TextView itemName = findViewById(R.id.confirm_item_name);
        itemName.setText(itemData.getName());
        //Image
        ImageView itemImage = findViewById(R.id.confirm_item_image);
        Glide.with(this).load(imageURL).into(itemImage);
//        itemImage
        //Dates
        TextView itemDates = findViewById(R.id.confirm_date_date);
        itemDates.setText( startDate +" - "+ endDate );
        //Price
        TextView itemPrice = findViewById(R.id.confirm_price_price);
        DecimalFormat df = new DecimalFormat("#.00");
        itemPrice.setText("€" + df.format(itemData.getPrice()));
        //Total
        TextView itemTotal = findViewById(R.id.confirm_total_price);
        itemTotal.setText("€" + df.format((itemData.getPrice() + 2)));

        //Initialize new Instance of Lendz
        Helper helper = new Helper();
        newLenzDataItem = helper.new LendzData(itemData.getId(), MainActivity.USER.getId(), startDate, endDate);

    }

    //Complete Borrow And Start Receipt Activity
    private void borrow() {
        try {
            String HTTP_REQUEST_BASE_URL = "https://lend-app.herokuapp.com/";
            String LENDZ_PATH = "borrowed";
            Gson gson = new Gson();
            final String requestBody = gson.toJson(newLenzDataItem);

            RequestQueue req = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTP_REQUEST_BASE_URL + LENDZ_PATH, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    finish();
                    Intent receiptIntent = new Intent(getBaseContext(), ExploreReceiptActivity.class);
                    receiptIntent.putExtra("itemData", (Parcelable) itemData);
                    receiptIntent.putExtra("datesBtn", startDate);
                    receiptIntent.putExtra("endDate", endDate);
                    receiptIntent.putExtra("imageURL", imageURL);
                    startActivity(receiptIntent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", error + "");
                }
            })
            {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };
            req.add(stringRequest);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("JSONERROR", e + "");
        }
    }


}
