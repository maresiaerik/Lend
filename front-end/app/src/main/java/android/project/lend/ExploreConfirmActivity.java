package android.project.lend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExploreConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_confirm);

        //Setting Cancel Button To Go Back
        Button cancelBtn = findViewById(R.id.confirm_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent ExploreIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(ExploreIntent);
            }
        });

        //Setting Borrow Button To Complete Order + Show Receipt
        Button borrowBtn = findViewById(R.id.confirm_borrow_btn);
        borrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent receiptIntent = new Intent(getBaseContext(), ExploreReceiptActivity.class);
                startActivity(receiptIntent);
            }
        });

    }
}
