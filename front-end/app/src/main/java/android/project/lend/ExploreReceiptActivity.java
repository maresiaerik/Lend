package android.project.lend;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ExploreReceiptActivity extends AppCompatActivity {

    String number, startDate, endDate, imageURL, userEmail, userAddress;
    TextView itemEmail, itemPhone;
    ProductCore itemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_receipt);
        Intent intent = getIntent();
        itemData = intent.getParcelableExtra("itemData");
        startDate = intent.getStringExtra("datesBtn");
        endDate = intent.getStringExtra("endDate");
        imageURL = intent.getStringExtra("imageURL");
        userEmail = intent.getStringExtra("userEmail");
        userAddress = intent.getStringExtra("userAddress");
        number = intent.getStringExtra("userPhone");
        if((itemData != null) && (startDate != null) && (endDate != null)){
            setReceiptDetails();
        }

        //Setting Page Title
        TextView pageTitle = findViewById(R.id.page_title);
        pageTitle.setText("Receipt");

        //Setting Fragment To Lendz For Main Activity
        FragmentHandler.setCurrentFragment(1);

        //Set Intent For MainActivity
        final Intent mainIntent = new Intent(this, MainActivity.class);

        //Set OK To Return To Lendz Fragment
        Button okBtn = findViewById(R.id.receipt_ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ActivityOptions options = ActivityOptions.makeCustomAnimation(ExploreReceiptActivity.this, R.anim.in_top, R.anim.out_bottom);
                startActivity(mainIntent, options.toBundle());
            }
        });

        //Set Location To Open Maps Directions
        TextView location = findViewById(R.id.receipt_location_location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });

        //Get Contact Number
        itemPhone = findViewById(R.id.receipt_contact_number);
        itemPhone.setText(number);


        //Set Contact Number To Open Dialog For Call Or Text
        itemPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactDialog();
            }
        });

        //Set Email To Open Email Intent
        itemEmail = findViewById(R.id.receipt_contact_email);
        itemEmail.setText(userEmail);
        itemEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmail();
            }
        });
    }

    //Setting Receipt Details From Intent Extras
    private void setReceiptDetails() {

        //Image
        ImageView img = findViewById(R.id.receipt_item_image);
        Glide.with(this).load(imageURL).into(img);
        //Name
        TextView itemName = findViewById(R.id.receipt_item_name);
        itemName.setText(itemData.getName());
        //Price
        TextView itemPrice = findViewById(R.id.receipt_price_price);
        DecimalFormat df = new DecimalFormat("#.00");

        Float multiplier = getMultiplier();
        Float price = getPrice(multiplier);

        itemPrice.setText("€" + df.format(price));

        //Dates
        //From
        TextView itemFrom = findViewById(R.id.receipt_from_date);
        itemFrom.setText(startDate);
        //Until
        TextView itemUntil = findViewById(R.id.receipt_until_date);
        itemUntil.setText(endDate);
        //Address
        TextView itemLocation = findViewById(R.id.receipt_location_location);
        itemLocation.setText(userAddress);
        //Phone
         // itemPhone.setText(itemData.);
        //Email
//        itemEmail.setText();
    }

    //Start Map Intent With Address
    private void openMap() {

        String uri = "geo:0,0?q=" + userAddress;
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(mapIntent);
    }

    //Open Contact Dialog To Select Phone Or Text Message
    private void openContactDialog() {
        final Dialog contactDialog = new Dialog(this);
        contactDialog.setContentView(R.layout.contact_layout);
        contactDialog.show();
        //Set Phone Icon To Launch Phone Intent
        contactDialog.findViewById(R.id.phone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + number);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, u);
                startActivity(callIntent);
                contactDialog.dismiss();
            }
        });
        //Set Message Icon To Launch SMS Intent
        contactDialog.findViewById(R.id.gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(Intent.ACTION_VIEW);
                messageIntent.setData(Uri.parse("sms:" + number));
                startActivity(messageIntent);
                contactDialog.dismiss();
            }
        });

    }

    //Start Email Intent
    private void openEmail() {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", userEmail, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lend: ");
        startActivity(emailIntent);
    }


    private Float getMultiplier() {
        Float price = itemData.getPrice();
        Double percentageMultiplier = 1.0;
        if(price <= 10) {
            percentageMultiplier = 1.0;
        }
        else if(price > 10 && price <= 50){
            percentageMultiplier = 1.05;
        }
        else if (price > 50 ) {
            percentageMultiplier = 1.1;

        }

        return new Float(percentageMultiplier);
    }

    private Float getPrice(Float multiplier) {
        Date start;
        Date end;
        long diff;
        long noOfDays = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            start = sdf.parse(startDate);
            end =  sdf.parse(endDate);
            diff = end.getTime() - start.getTime();
            noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Float serviceFee = (multiplier - 1) * itemData.getPrice() + 1;

        return itemData.getPrice() *  (noOfDays == 0 ? 1 : noOfDays) + serviceFee;
    }
}
