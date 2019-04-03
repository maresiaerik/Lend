package android.project.lend;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class HomeUserRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText register_card_start = new EditText(this);
        EditText register_card_end = new EditText(this);
        EditText register_card_csv = new EditText(this);

        setContentView(register_card_csv);
        setContentView(R.layout.fragment_home_user_edit);
    }
}
