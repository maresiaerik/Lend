package android.project.lend;

import android.media.Image;
import android.service.autofill.UserData;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Helper {
    private String HTTP_BASE_URL = "https://lend-app.herokuapp.com/";

    public void getProductData(final IManager manager, final ArrayList<ProductData> productList){

        final RequestQueue req = Volley.newRequestQueue(MainActivity.mainActivityContext);
        String productPath = "items";

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, HTTP_BASE_URL + productPath, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ProductData>>(){}.getType();
                ArrayList<ProductData> responseProductList = gson.fromJson(response, listType);
                for (ProductData product : responseProductList) {
                    productList.add(product);
                }
                manager.setLoaded(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PRODUCT_ERROR", error + "");

            }
        });

        req.add(stringRequest);
    }

    public UserData getUserData(Integer id){
/*
        UserData userData = new UserData();

        userData.id = 1;
        userData.firstName = "Florian";
        userData.lastName = "Brandsma";
        userData.imageUrl = "https://secure.i.telegraph.co.uk/multimedia/archive/03597/POTD_chick_3597497k.jpg";
        userData.emailAddress = "t7brfl00@students.oamk.fi";
        userData.homeAddress = "Kotkantie 1";
        userData.phoneNumber = "+3581234567890";
        userData.cardNumber = "1234 4567 8910";
        userData.cardDate = "01/20";
        userData.cardSecurity = "123";
*/
        return null;
    }

    public void getUserData(final IManager manager, final ArrayList<UserData> userList){

        final RequestQueue req = Volley.newRequestQueue(MainActivity.mainActivityContext);
        String users = "users";

        final StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, HTTP_BASE_URL + users, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<UserData>>(){}.getType();
                ArrayList<UserData> responseUser = gson.fromJson(response, listType);

                for (UserData user: responseUser) {
                    userList.add(user);
                }

                manager.setLoaded(true);
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("IN_USER_ERROR", error + "");
                }
            }
        );

        req.add(stringRequest);
    }

    public void getImageData(final IManager manager, final ArrayList<ImageData> imageList){

        final RequestQueue req = Volley.newRequestQueue(MainActivity.mainActivityContext);

        String images = "images";
        final StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, HTTP_BASE_URL + images, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<ImageData>>(){}.getType();
                ArrayList<ImageData> responseImageData = gson.fromJson(response, listType);


                for (ImageData img : responseImageData) {
                    imageList.add(img);
                }
                manager.setLoaded(true);
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("IN_IMAGE_ERROR", error + "");
                }
            }
        );

        req.add(stringRequest);
    }

    public void getLendzData(final IManager manager, final ArrayList<LendzData> lendzList) {

        final RequestQueue req = Volley.newRequestQueue(MainActivity.mainActivityContext);
        String borrowed = "borrowed";

        final StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, HTTP_BASE_URL + borrowed, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<LendzData>>(){}.getType();
                ArrayList<LendzData> responseLendz = gson.fromJson(response, listType);

                for (LendzData lendz: responseLendz) {
                    lendzList.add(lendz);
                }

                manager.setLoaded(true);
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("IN_ERROR", error + "");
                }
            }
        );

        req.add(stringRequest);
    }

    public class ProductData{

        @SerializedName("id_item")
        Integer id;
        @SerializedName("user_id")
        Integer userId;
        String name;
        Float price;
        @SerializedName("ratings")
        Integer rating;
        String imageUrl;
        String description;
        String category;


        public ProductData(Integer id, Integer userId, String name, Float price, Integer rating, String description, String category) {
            this.id = id;
            this.userId = userId;
            this.name = name;
            this.price = price;
            this.rating = rating;
            this.description = description;
            this.category = category;
        }
        public ProductData(Integer userId, String name, Float price, Integer rating, String description, String category) {
            this.userId = userId;
            this.name = name;
            this.price = price;
            this.rating = rating;
            this.description = description;
            this.category = category;
        }
    }

    public class UserData{

        @SerializedName("id_user")
        Integer id;
        @SerializedName("fname")
        String firstName;
        @SerializedName("lname")
        String lastName;
        @SerializedName("pic")
        String imageUrl;
        @SerializedName("email")
        String emailAddress;
        @SerializedName("address")
        String homeAddress;
        @SerializedName("phone")
        String phoneNumber;
        @SerializedName("card_num")
        String cardNumber;
        @SerializedName("card_date")
        String cardDate;
        @SerializedName("card_sec")
        String cardSecurity;
        String password;

        public UserData(Integer id, String firstName, String lastName, String imageUrl, String emailAddress, String homeAddress, String phoneNumber, String cardNumber, String cardDate, String cardSecurity) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.imageUrl = imageUrl;
            this.emailAddress = emailAddress;
            this.homeAddress = homeAddress;
            this.phoneNumber = phoneNumber;
            this.cardNumber = cardNumber;
            this.cardDate = cardDate;
            this.cardSecurity = cardSecurity;
        }
        public UserData( String firstName, String lastName, String imageUrl, String emailAddress, String homeAddress, String phoneNumber, String cardNumber, String cardDate, String cardSecurity, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.imageUrl = imageUrl;
            this.emailAddress = emailAddress;
            this.homeAddress = homeAddress;
            this.phoneNumber = phoneNumber;
            this.cardNumber = cardNumber;
            this.cardDate = cardDate;
            this.cardSecurity = cardSecurity;
            this.password = password;
        }
    }

    public class ImageData{

        @SerializedName("id_image")
        Integer id;
        @SerializedName("item_id")
        Integer productId;
        String url;

        public ImageData(Integer id, Integer productId, String url) {
            this.id = id;
            this.productId = productId;
            this.url = url;
        }

        public ImageData( Integer productId, String url) {
            this.productId = productId;
            this.url = url;
        }
    }

    public class LendzData{

        @SerializedName("id_borrowed")
        Integer id;
        @SerializedName("item_id")
        Integer productId;
        @SerializedName("user_id")
        Integer lenderUserId;
        @SerializedName("start_date")
        String startDate;
        @SerializedName("end_date")
        String dueDate;
        Integer rating;

        public LendzData(Integer id, Integer productId, Integer lenderUserId, String startDate, String dueDate, Integer rating) {
            this.id = id;
            this.productId = productId;
            this.lenderUserId = lenderUserId;
            this.startDate = startDate;
            this.dueDate = dueDate;
            this.rating = rating;
        }
        public LendzData(Integer productId, Integer lenderUserId, String startDate, String dueDate, Integer rating) {
            this.productId = productId;
            this.lenderUserId = lenderUserId;
            this.startDate = startDate;
            this.dueDate = dueDate;
            this.rating = rating;
        }
    }
}
