package android.project.lend;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
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

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ExploreConfirmActivity extends AppCompatActivity {

    ProductCore itemData;
    ProductDataItem productDataItem;
    String startDate, endDate, userEmail, userAddress, userPhone;
    String imageURL;

    Helper.LendzData newLenzDataItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_confirm);

        //Setting Title
        TextView pageTitle = findViewById(R.id.page_title);
        pageTitle.setText("Confirm");

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
        if (MainActivity.USER != null && MainActivity.USER.getId() != null/*false*/) {
            borrowBtn.setText("Borrow");
            borrowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrow();
                }
            });
        } else {
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
        userAddress = intent.getStringExtra("userAddress");
        userEmail = intent.getStringExtra("userEmail");
        userPhone = intent.getStringExtra("userPhone");

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
        //Dates
        TextView itemDates = findViewById(R.id.confirm_date_date);
        itemDates.setText(startDate + " - " + endDate);
        //Price
        TextView itemPrice = findViewById(R.id.confirm_price_price);
        TextView servicePrice = findViewById(R.id.confirm_service_price);
        TextView pricePerDay = findViewById(R.id.price_per_day);
        //Get Lendz length in days
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date start;
        Date end;
        long diff;
        long noOfDays = 1;

        try {
            start = sdf.parse(startDate);
            end = sdf.parse(endDate);
            diff = end.getTime() - start.getTime();
            noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DecimalFormat df = new DecimalFormat("#.00");
        Float multiplier = getMultiplier();

        Float serviceFee = (multiplier - 1) * itemData.getPrice() + 1;

        servicePrice.setText("€" + df.format(serviceFee));

        //Total
        TextView itemTotal = findViewById(R.id.confirm_total_price);
        pricePerDay.setText("€" + df.format(itemData.getPrice()) + "/day");
        itemPrice.setText("€" + df.format(itemData.getPrice() * (noOfDays == 0 ? 1 : noOfDays)));
        itemTotal.setText("€" + df.format((itemData.getPrice() * (noOfDays == 0 ? 1 : noOfDays) + serviceFee)));

        //Initialize new Instance of Lendz
        if (MainActivity.USER != null && MainActivity.USER.getId() != null) {
            Helper helper = new Helper();
            newLenzDataItem = helper.new LendzData(itemData.getId(), MainActivity.USER.getId(), startDate, endDate, null);
        }
    }

    private Float getMultiplier() {

        Float price = itemData.getPrice();

        Double percentageMultiplier = 1.0;

        if (price <= 10) {
            percentageMultiplier = 1.0;
        } else if (price <= 50) {
            percentageMultiplier = 1.05;
        } else {
            percentageMultiplier = 1.1;
        }

        return new Float(percentageMultiplier);
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
                    receiptIntent.putExtra("userEmail", userEmail);
                    receiptIntent.putExtra("userPhone", userPhone);
                    receiptIntent.putExtra("userAddress", userAddress);
                    startActivity(receiptIntent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", error + "");
                }
            }) {
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
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("JSONERROR", e + "");
        }
    }


}
