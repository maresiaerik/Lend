package android.project.lend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

public class HomeUserRegister extends AppCompatActivity {

    EditText firstName;
    EditText secondName;
    EditText emailAddress;
    EditText phoneNumber;
    EditText address;
    EditText cardNumber;
    EditText cardExpiry;
    EditText cardCSV;
    EditText password;
    EditText passwordConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_home_user_edit);

        //Get First Name
        firstName = findViewById(R.id.user_first_name_edit);

        //Get Second Name
        secondName = findViewById(R.id.user_second_name_edit);

        //Get Pic

        //Get Email Address
        emailAddress = findViewById(R.id.user_email_edit);

        //Get Phone Number
        phoneNumber = findViewById(R.id.user_phone_edit);

        //Get Address
        address = findViewById(R.id.user_address_edit);

        //Get Credit Card Number
        cardNumber = findViewById(R.id.user_card_edit);

        //Get Credit Card Expiry
        cardExpiry = findViewById(R.id.register_card_expiry);

        //Get Credit Card CSV
        cardCSV = findViewById(R.id.register_card_csv);

        //Get Password
        password = findViewById(R.id.register_password);

        //Get Confirm Password
        passwordConfirm = findViewById(R.id.register_password_confirm);


        //Set Cancel Button Listener
        Button cancelBtn = findViewById(R.id.register_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Set Register Button Listener
        Button registerBtn = findViewById(R.id.register_submit_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {

        try {

            Helper helper = new Helper();
            String url = "placeholder_url"; //placeholder
            Helper.UserData user = helper.new UserData(
                    firstName.getText().toString(),
                    secondName.getText().toString(),
                    url,
                    emailAddress.getText().toString(),
                    address.getText().toString(),
                    phoneNumber.getText().toString(),
                    cardNumber.getText().toString(),
                    cardExpiry.getText().toString(),
                    cardCSV.getText().toString(),
                    password.getText().toString());


            Gson gson = new Gson();
            RequestQueue req = Volley.newRequestQueue(this);

            final String requestBody = gson.toJson(user);
            String HTTP_BASE_URL = "https://lend-app.herokuapp.com/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTP_BASE_URL + "users", new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson g = new Gson();
                    ResponseObject res = g.fromJson(response, ResponseObject.class);

                    onBackPressed();
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", error+ "");
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
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("JSONERROR", e + "");
        }
    }
}
