package android.project.lend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeUserLoginActivity extends AppCompatActivity {

    ArrayList<UserDataItem> userDataItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user_login);


        //userDataItem = userManager.getUserList();
        //Set Cancel Button To Go Back
        Button cancelBtn = findViewById(R.id.login_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        userDataItem = MainActivity.userManager.getUserList();
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
            Boolean userFound = false;
            Log.d("userlist", userDataItem.size() + "");

            for (int i = 0; i < userDataItem.size(); i++) {

                if (userDataItem.get(i).getEmailAddress().equals(email) && userDataItem.get(i).getPassword().equals(password)) {
                    MainActivity.USER = new UserDataItem();
                    UserDataItem userData = userDataItem.get(i);
                    Helper helper = new Helper();
                    Helper.UserData user = helper.new UserData(
                            userData.getId(),
                            userData.getFirstName(),
                            userData.getLastName(),
                            userData.getImageUrl(),
                            userData.getEmailAddress(),
                            userData.getHomeAddress(),
                            userData.getPhoneNumber(),
                            userData.getCardNumber(),
                            userData.getCardDate(),
                            userData.getCardSecurity(),
                            userData.getPassword()
                    );

                    MainActivity.USER.create(user);
                    userFound = true;

                    SharedPreferences settings = getSharedPreferences("USER", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("id", user.id);
                    editor.putString("firstname",user.firstName);
                    editor.putString("lastname",user.lastName);
                    editor.putString("email",user.emailAddress);
                    editor.putString("addressET",user.homeAddress);
                    editor.putString("url",user.imageUrl);
                    editor.putString("phone",user.phoneNumber);
                    editor.putString("card_num",user.cardNumber);
                    editor.putString("card_date",user.cardDate);
                    editor.putString("card_sec",user.cardSecurity);
                    editor.putString("card_sec",user.cardSecurity);
                    editor.putString("passwordET",user.password);
                    editor.commit();

                }
            }
            /*TODO Login Process*/
            /*
            Log.d("IN_HERER", "I AM HERE");

            UserManager userManager = new UserManager(null, null);
            ArrayList<UserDataItem> userList = userManager.getUserList();
            Boolean isUser = false;
            for (UserDataItem userEmail : userList) {

                for (UserDataItem userPassword : userList) {
                    if(userEmail.getEmailAddress().equals(email) && userPassword.getPassword().equals(passwordET)) {
                        isUser = true;
                    }
                }
            }
*/
            if (userFound) {
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
        Intent registerIntent = new Intent(this, HomeUserRegisterFragment.class);
        startActivity(registerIntent);
    }

}
