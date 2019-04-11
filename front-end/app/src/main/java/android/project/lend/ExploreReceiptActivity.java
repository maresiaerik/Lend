package android.project.lend;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

public class ExploreReceiptActivity extends AppCompatActivity {

    String number, startDate, endDate, imageURL;
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
        if((itemData != null) && (startDate != null) && (endDate != null)){
            setReceiptDetails();
        }

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
                startActivity(mainIntent);
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
        number = itemPhone.getText().toString();

        //Set Contact Number To Open Dialog For Call Or Text
        itemPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactDialog();
            }
        });

        //Set Email To Open Email Intent
        itemEmail = findViewById(R.id.receipt_contact_email);
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
        itemPrice.setText("€"+df.format((itemData.getPrice()+2)));
        //Dates
        //From
        TextView itemFrom = findViewById(R.id.receipt_from_date);
        itemFrom.setText(startDate);
        //Until
        TextView itemUntil = findViewById(R.id.receipt_until_date);
        itemUntil.setText(endDate);
        //Address
        TextView itemLocation = findViewById(R.id.receipt_location_location);
        //Phone
//        itemPhone.setText();
        //Email
//        itemEmail.setText();
    }

    //Start Map Intent With Address
    private void openMap() {
        TextView addressView = findViewById(R.id.receipt_location_location);
        String address = addressView.getText().toString();
        String uri = "geo:0,0?q=" + address;
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
        contactDialog.findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
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
        String emailAddress = itemEmail.getText().toString();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", emailAddress, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lend: ");
        startActivity(emailIntent);
    }
}
