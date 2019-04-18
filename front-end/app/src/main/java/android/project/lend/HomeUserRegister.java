package android.project.lend;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class HomeUserRegister extends AppCompatActivity implements IDataController {

    EditText firstNameET, secondNameET, emailAddressET, phoneNumberET, addressET, cardNumberET,
            cardExpiryET, cardCSVET, passwordET, passwordConfirmET;
    String firstName, secondName, emailAddress, phoneNumber, address, cardNumber, cardExpiry,
            cardCSV, password, passwordConfirm;
    Boolean firstNameCheck, secondNameCheck, emailAddressCheck, phoneNumberCheck, addressCheck, cardNumberCheck,
            cardExpiryCheck, cardCSVCheck, passwordCheck, passwordConfirmCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_home_user_edit);

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
                check();
            }
        });
    }

    private void check() {
        firstNameCheck = secondNameCheck = emailAddressCheck = phoneNumberCheck = addressCheck = cardNumberCheck
                = cardExpiryCheck = cardCSVCheck = passwordCheck = passwordConfirmCheck = false;

        //Get First Name
        firstNameET = findViewById(R.id.user_first_name_edit);
        firstName = firstNameET.getText().toString();
        if (firstName.length() > 0)
            firstNameCheck = true;

        //Get Second Name
        secondNameET = findViewById(R.id.user_second_name_edit);
        secondName = secondNameET.getText().toString();
        if (firstNameCheck && secondName.length() > 0)
            secondNameCheck = true;

        //Get Email Address
        emailAddressET = findViewById(R.id.user_email_edit);
        emailAddress = emailAddressET.getText().toString();
        if (secondNameCheck && emailAddress.length() > 0)
            emailAddressCheck = true;

        //Get Phone Number
        phoneNumberET = findViewById(R.id.user_phone_edit);
        phoneNumber = phoneNumberET.getText().toString();
        if (emailAddressCheck && phoneNumber.length() > 0)
            phoneNumberCheck = true;

        //Get Address
        addressET = findViewById(R.id.user_address_edit);
        address = addressET.getText().toString();
        if (phoneNumberCheck && address.length() > 0)
            addressCheck = true;

        //Get Credit Card Number
        cardNumberET = findViewById(R.id.user_card_edit);
        cardNumber = cardNumberET.getText().toString();
        if (addressCheck && cardNumber.length() > 0)
            cardNumberCheck = true;

        //Get Credit Card Expiry
        cardExpiryET = findViewById(R.id.register_card_expiry);
        cardExpiry = cardExpiryET.getText().toString();
        if (cardNumberCheck && cardExpiry.length() > 0)
            cardExpiryCheck = true;

        //Get Credit Card CSV
        cardCSVET = findViewById(R.id.register_card_csv);
        cardCSV = cardCSVET.getText().toString();
        if (cardExpiryCheck && cardCSV.length() > 0)
            cardCSVCheck = true;

        //Get Password
        passwordET = findViewById(R.id.register_password);
        password = passwordET.getText().toString();
        if (cardCSVCheck && password.length() > 0)
            passwordCheck = true;

        //Get Confirm Password
        passwordConfirmET = findViewById(R.id.register_password_confirm);
        passwordConfirm = passwordConfirmET.getText().toString();
        if (passwordCheck && passwordConfirm.length() > 0) {
            passwordConfirmCheck = true;
            if (password.equals(passwordConfirm)) {
                register();
            } else
                Toast.makeText(getBaseContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getBaseContext(), "Please complete all fields", Toast.LENGTH_LONG).show();
    }

    private void register() {

        try {

            Helper helper = new Helper();
            String url = "placeholder_url"; //placeholder
            final Helper.UserData user = helper.new UserData(
                    firstName, secondName, address, emailAddress, url, phoneNumber, cardNumber,
                    cardExpiry, cardCSV, password);
            Gson gson = new Gson();
            RequestQueue req = Volley.newRequestQueue(this);

            final String requestBody = gson.toJson(user);
            String HTTP_BASE_URL = "https://lend-app.herokuapp.com/";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTP_BASE_URL + "users", new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson g = new Gson();
                    ItemResponse res = g.fromJson(response, ItemResponse.class);
                    SharedPreferences settings = getSharedPreferences("USER", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    user.id = Integer.valueOf(res.getInsertId());
                    editor.putInt("id", user.id);
                    editor.commit();
                    MainActivity.USER = new UserDataItem();
                    MainActivity.USER.create(user);
                    onBackPressed();
                }
            }, new com.android.volley.Response.ErrorListener() {
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

    @Override
    public void setData() {

    }
}
