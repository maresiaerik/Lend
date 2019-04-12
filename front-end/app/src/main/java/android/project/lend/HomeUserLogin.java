package android.project.lend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeUserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user_login);

        //Set Cancel Button To Go Back
        Button cancelBtn = findViewById(R.id.login_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Set Login Button To Login User + Navigate Back To Confirm Activity
        Button loginBtn = findViewById(R.id.login_login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //Set Register Button To Open Register Activity
        Button registerBtn = findViewById(R.id.login_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }


    //Login Process
    private void login() {
        EditText emailIn = findViewById(R.id.login_email);
        String email = emailIn.getText().toString();
        EditText passwordIn = findViewById(R.id.login_password);
        String password = passwordIn.getText().toString();
        if (email.length() > 0 && password.length() > 0) {
            /*TODO Login Process*/




            if (/*success*/true) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Email/Password Incorrect", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Email/Password Incomplete", Toast.LENGTH_SHORT).show();
        }
    }

    //Start Intent For Register Activity
    private void register() {
        finish();/* Close Login Page So User Goes Back To Confirm Page Upon Registration */
        Intent registerIntent = new Intent(this, HomeUserRegister.class);
        startActivity(registerIntent);
    }

}
