package android.project.lend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeUserRegister extends AppCompatActivity {

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
                register();
            }
        });


    }

    private void register() {

        //Get First Name
        EditText firstName = findViewById(R.id.user_first_name_edit);

        //Get Second Name
        EditText secondName = findViewById(R.id.user_second_name_edit);

        //Get Pic

        //Get Email Address
        EditText emailAddress = findViewById(R.id.user_email_edit);

        //Get Phone Number
        EditText phoneNumber = findViewById(R.id.user_phone_edit);

        //Get Address
        EditText address = findViewById(R.id.user_address_edit);

        //Get Credit Card Number
        EditText cardNumber = findViewById(R.id.user_card_edit);

        //Get Credit Card Expiry
        EditText cardExpiry = findViewById(R.id.register_card_expiry);

        //Get Credit Card CSV
        EditText cardCSV = findViewById(R.id.register_card_csv);

        //Get Password
        EditText password = findViewById(R.id.register_password);

        //Get Confirm Password
        EditText passwordConfirm = findViewById(R.id.register_password_confirm);

    }
}
