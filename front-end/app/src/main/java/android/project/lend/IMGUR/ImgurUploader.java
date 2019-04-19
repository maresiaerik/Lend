package android.project.lend.IMGUR;

import android.content.Context;
import android.graphics.Bitmap;
import android.project.lend.BuildConfig;
import android.project.lend.Helper;
import android.project.lend.MainActivity;
import android.project.lend.UserDataItem;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class ImgurUploader {

    final String apiKey = BuildConfig.ApiKey;
    final String iUrl = "https://api.imgur.com/3/image";
    final String hUrl = MainActivity.BASE_URL;
    String imgurUrl;
    private Context context;
    Helper.ImageData newImage;
    RequestQueue reqQueue;

    public static String get64BaseImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void uploadImgur(final Bitmap bitmap, final boolean product, final String id, Context context) {
        this.context = context;
        reqQueue = Volley.newRequestQueue(context.getApplicationContext());
        //Convert Image Bitmap To Binary String
        final String imageString = get64BaseImage(bitmap);
        StringRequest request = new StringRequest(Request.Method.POST, iUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                Imgur_API_GSON imgur_gson = gson.fromJson(s, Imgur_API_GSON.class);
                imgurUrl = (imgur_gson.getData().getLink());

                if (product) {
                    Helper helper = new Helper();
                    Integer parsedId = Integer.valueOf(id);
                    newImage = helper.new ImageData(parsedId, imgurUrl);
                    uploadImageHeroku(newImage);
                } else {
                    updateUser(imgurUrl);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("VOLLEY_ERROR", volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("image", imageString);
                return parameters;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Client-ID " + apiKey);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        reqQueue.add(request);
    }

    private void uploadImageHeroku(Helper.ImageData newImage) {
        try {
            String imagePath = "images";
            final Gson gson = new Gson();
            final String requestBody = gson.toJson(newImage);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, hUrl + imagePath, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Volley", response);
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
            reqQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("JSONERROR", e + "");
        }
    }

    private void updateUser(String imgurUrl) {
        UserDataItem u = MainActivity.USER;
        Helper helper = new Helper();
        final Helper.UserData user = helper.new UserData(
                u.getId(), u.getFirstName(), u.getLastName(), imgurUrl, u.getEmailAddress(), u.getHomeAddress(), u.getPhoneNumber(),
                u.getCardNumber(), u.getCardDate(), u.getCardSecurity(), u.getPassword());
        try {
            Gson gson = new Gson();
            final String requestBody = gson.toJson(user);

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, hUrl + "users/" + u.getId(), new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Here", response);
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
            reqQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("JSONERROR", e + "");
        }
    }

}
