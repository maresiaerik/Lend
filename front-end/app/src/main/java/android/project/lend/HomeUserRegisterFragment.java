package android.project.lend;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.project.lend.IMGUR.ImgurUploader;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

public class HomeUserRegisterFragment extends AppCompatActivity {

    EditText firstNameET, secondNameET, emailAddressET, phoneNumberET, addressET, cardNumberET,
            cardExpiryET, cardCSVET, passwordET, passwordConfirmET;
    String firstName, secondName, emailAddress, phoneNumber, address, cardNumber, cardExpiry,
            cardCSV, password, passwordConfirm, currentPhotoPath;
    Boolean firstNameCheck, secondNameCheck, emailAddressCheck, phoneNumberCheck, addressCheck, cardNumberCheck,
            cardExpiryCheck, cardCSVCheck, passwordCheck, passwordConfirmCheck, pictureCheck;
    final String imageDirectory = "/Lend";
    Bitmap finalBitmap;
    ImageView userImage;
    int gallery = 1, camera = 2, PERMISSION_ALL = 1;
    Uri photoURI;
    String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_home_user_edit);

        //Disable logout button
        Button logoutBtn = findViewById(R.id.edit_logout_btn);
        logoutBtn.setVisibility(View.GONE);

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

        userImage = findViewById(R.id.user_profile_pic_edit);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
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
            String imageUrl = "placeholder_url"; //placeholder
            final Helper.UserData user = helper.new UserData(
                    firstName, secondName, imageUrl, emailAddress, address, phoneNumber, cardNumber,
                    cardExpiry, cardCSV, password);
            Gson gson = new Gson();
            RequestQueue req = Volley.newRequestQueue(this);
            final String requestBody = gson.toJson(user);
            String url = MainActivity.BASE_URL;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url + "users", new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson g = new Gson();
                    ItemResponse res = g.fromJson(response, ItemResponse.class);
                    SharedPreferences settings = getSharedPreferences("USER", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    user.id = Integer.valueOf(res.getInsertId());
                    if (pictureCheck) {
                        uploadImage(res.getInsertId());
                    }
                    editor.putInt("id", user.id);
                    editor.putString("firstname", user.firstName);
                    editor.putString("lastname", user.lastName);
                    editor.putString("email", user.emailAddress);
                    editor.putString("address", user.homeAddress);
                    editor.putString("url", user.imageUrl);
                    editor.putString("phone", user.phoneNumber);
                    editor.putString("card_num", user.cardNumber);
                    editor.putString("card_date", user.cardDate);
                    editor.putString("card_sec", user.cardSecurity);
                    editor.putString("card_sec", user.cardSecurity);
                    editor.putString("password", user.password);
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

    private void uploadImage(String userID) {
        ImgurUploader uploadImage = new ImgurUploader();
        uploadImage.uploadImgur(finalBitmap, false, userID, getBaseContext());
    }

    //Checking Permissions
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showPictureDialog() {
        if (!hasPermissions(getBaseContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            showPictureDialog();
        } else {
            final Dialog pictureDialog = new Dialog(this);
            pictureDialog.setContentView(R.layout.picture_input_layout);
            pictureDialog.show();
            //Get Dialog Camera Icon
            pictureDialog.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePhotoFromCamera();
                    pictureDialog.dismiss();
                }

            });
            //Get Dialog Gallery Icon
            pictureDialog.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choosePhotoFromGallery();
                    pictureDialog.dismiss();
                }
            });
        }
    }

    //Start Gallery Intent
    public void choosePhotoFromGallery() {
        Toast.makeText(getBaseContext(), "Opening Gallery", Toast.LENGTH_SHORT).show();
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, gallery);
    }

    //Start Camera Intent
    public void takePhotoFromCamera() {
        Toast.makeText(getBaseContext(), "Starting Camera", Toast.LENGTH_SHORT).show();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getBaseContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getBaseContext(),
                        "android.project.lend",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, camera);
            }
        }
    }

    //Start Activity Result Listener
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == gallery) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), contentURI);
                    Toast.makeText(getBaseContext(), "Image Saved", Toast.LENGTH_SHORT).show();
                    saveSelector(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == camera) {
            Uri imageUri = null;
            if (data != null) {
                imageUri = data.getData();
            }
            if (imageUri == null && photoURI != null) {
                imageUri = photoURI;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), imageUri);
                    saveSelector(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Create Image File
    private File createImageFile() throws IOException {
        File image = null;
        File storageDir = new File(
                Environment.getExternalStorageDirectory() + imageDirectory);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        try {
            String imageFileName = "JPEG_" + Calendar.getInstance().getTimeInMillis() + "_";
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
            currentPhotoPath = image.getAbsolutePath();
            galleryAddPic();
        } catch (IOException ex) {
        }
        return image;
    }

    //Save Image To Gallery
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getBaseContext().sendBroadcast(mediaScanIntent);
    }

    //Save Bitmap Icon And Save To Relevant ImageView
    private void saveSelector(Bitmap bitmap) {
        float cx = bitmap.getWidth() / 2f;
        float cy = bitmap.getHeight() / 2f;
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
//        matrix.postScale(-1, 1, cx, cy);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        userImage.setImageBitmap(rotatedBitmap);
        pictureCheck = true;
        finalBitmap = rotatedBitmap;
    }


}
