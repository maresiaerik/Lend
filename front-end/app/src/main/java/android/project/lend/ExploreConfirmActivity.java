package android.project.lend;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExploreConfirmActivity extends AppCompatActivity {

    ProductCore itemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_confirm);


        //Setting Cancel Button To Go Back
        Button cancelBtn = findViewById(R.id.confirm_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Checking For User + Setting Button Text + Listener
        Button borrowBtn = findViewById(R.id.confirm_borrow_btn);
        if (MainActivity.USER.getId()>0/*false*/) {
            borrowBtn.setText("Borrow");
            borrowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrow();
                }
            });
        }else{
            borrowBtn.setText("Login");
            borrowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });
            
        }

        //Get Item Data From Intent 
        Intent intent = getIntent();
        itemData = intent.getParcelableExtra("itemData");
        if (itemData != null) {
            setItemData();
        }


    }

    //Start Login Activity
    private void login() {
        Intent loginIntent = new Intent(this, HomeUserLogin.class);
        startActivity(loginIntent);

    }

    //Setting Item Details From Intent Extras
    private void setItemData() {
        //Name
        TextView itemName = findViewById(R.id.confirm_item_name);
        itemName.setText(itemData.getName());
        //Image
        ImageView itemImage = findViewById(R.id.confirm_item_image);
//        itemImage
        //Dates
        TextView itemDates = findViewById(R.id.confirm_date_date);
//        itemDates.setText( dateFrom +" - "+ dateUntil );
        //Price
        TextView itemPrice = findViewById(R.id.confirm_price_price);
        DecimalFormat df = new DecimalFormat("#.00");
        itemPrice.setText("€" + df.format(itemData.getPrice()));
        //Total
        TextView itemTotal = findViewById(R.id.confirm_total_price);
        itemTotal.setText("€" + df.format((itemData.getPrice() + 2)));

    }

    //Complete Borrow And Start Receipt Activity
    private void borrow() {
        finish();/*Close Confirm Activity/Remove From BackStack*/
        /*TODO CREATE LENDZ ENTRY
        int userId =  MainActivity.USER.getId();
        int itemId = itemData.getId();
        float totalPrice = (itemData.getPrice() + 2);
        String startDate = **from intent**
        String endDate =  **from intent**
        */
        Intent receiptIntent = new Intent(getBaseContext(), ExploreReceiptActivity.class);
        receiptIntent.putExtra("itemData", (Parcelable) itemData);
        startActivity(receiptIntent);
    }


}
