package android.project.lend.IMGUR;

import android.content.Context;
import android.project.lend.BuildConfig;
import android.project.lend.IMGUR.Imgur_API_GSON;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;


public class ImgurUploader {

    final String apiKey = BuildConfig.ApiKey;
    final String url = "https://api.imgur.com/3/image";
    String imgurUrl;
    private Context context;

    public void upload(final String imageString, final boolean product, int id, Context context) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Gson gson = new Gson();
                Imgur_API_GSON imgur_gson = gson.fromJson(s, Imgur_API_GSON.class);
                imgurUrl = (imgur_gson.getData().getLink());
                Log.d("IMGUR_TEST", imgurUrl);
                if (product) {
                    //Add To Image DB With ID
                } else {
                    //Update User DB With ID
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
        this.context = context;
        RequestQueue reqQueue = Volley.newRequestQueue(context.getApplicationContext());
        reqQueue.add(request);
    }

}
