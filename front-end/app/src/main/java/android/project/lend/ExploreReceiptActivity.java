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

public class ExploreReceiptActivity extends AppCompatActivity {

    String number;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_receipt);

        //Setting Fragment To Lendz For Main Activity
        FragmentManager.setCurrentFragment(1);

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
        TextView contactNumber = findViewById(R.id.receipt_contact_number);
        number = contactNumber.getText().toString();

        //Set Contact Number To Open Dialog For Call Or Text
        contactNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContactDialog();
            }
        });

        //Set Email To Open Email Intent
        email = findViewById(R.id.receipt_contact_email);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmail();
            }
        });
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
        String emailAddress = email.getText().toString();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", emailAddress, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lend: ");
        startActivity(emailIntent);
    }
}
