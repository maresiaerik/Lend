package android.project.lend;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class LendzDataItem extends LendzCore {

    public ProductDataItem product = new ProductDataItem();
    public UserDataItem lender = new UserDataItem();

    public void uploadRating(Float r, final View listItem) {
        RequestQueue req = Volley.newRequestQueue(listItem.getContext());
        final Integer rating = Math.round(r);
        Gson gson = new Gson();
        UploadRating item = new UploadRating(this.getId(), rating);
        final String requestBody = gson.toJson(item);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, MainActivity.BASE_URL + "borrowed", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(listItem.getContext(), "Rating saved", Toast.LENGTH_LONG).show();
                updateItemRating(listItem, rating);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("uploadRatingError", error + "");
                    }
                }){
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

    private void updateItemRating(View view, Integer givenRating) {
        RequestQueue req = Volley.newRequestQueue(view.getContext());
        Double averageRating = Double.valueOf(( product.getRating() + givenRating) / 2);
        Double newDoubleRating = Math.floor(averageRating);
        Integer newRating = Math.toIntExact(Math.round(newDoubleRating));
        product.setRating(newRating);
        Gson gson = new Gson();
        Helper helper = new Helper();
        Helper.ProductData item = helper.new ProductData(
                product.getId(),
                product.getUserId(),
                product.getName(),
                product.getPrice(),
                newRating,
                product.getDescription(),
                product.getCategory()
                );
        final String requestBody = gson.toJson(item);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, "https://lend-app.herokuapp.com/items", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE_HERE", response + "");
            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("UpdateItemRatingError", error +"");
            }
        })
        {
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

    private class UploadRating {
        @SerializedName("id_borrowed")
        Integer id;
        Integer rating;

        public UploadRating(Integer id, Integer rating) {
            this.id = id;
            this.rating = rating;
        }
    }
}
