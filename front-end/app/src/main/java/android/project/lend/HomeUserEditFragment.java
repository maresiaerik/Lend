package android.project.lend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeUserEditFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_user_edit, container, false);

        //Setting Image
        ImageView img = view.findViewById(R.id.user_profile_pic_edit);
        new ImageDownloader(img).execute(MainActivity.USER.getImageUrl());

        //Setting First Name
        EditText userFirstNameEdit = view.findViewById(R.id.user_first_name_edit);
        userFirstNameEdit.setText(MainActivity.USER.getFirstName());

        //Setting Second Name
        EditText userSecondNameEdit = view.findViewById(R.id.user_second_name_edit);
        userSecondNameEdit.setText(MainActivity.USER.getLastName());

        //Setting Email
        final TextView userEmailEdit = view.findViewById(R.id.user_email_edit);
        userEmailEdit.setText(MainActivity.USER.getEmailAddress());

        //Setting Phone
        TextView userPhoneEdit = view.findViewById(R.id.user_phone_edit);
        userPhoneEdit.setText(MainActivity.USER.getPhoneNumber());

        //Setting Email
        TextView userAddressEdit = view.findViewById(R.id.user_address_edit);
        userAddressEdit.setText(MainActivity.USER.getHomeAddress());

        //Setting Credit Card
        TextView userCardEdit = view.findViewById(R.id.user_card_edit);
        userCardEdit.setText(MainActivity.USER.getCardNumber());


        //Preventing Name Editing
        userFirstNameEdit.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.input_disabled));
        userFirstNameEdit.setFocusable(false);
        userFirstNameEdit.setFocusableInTouchMode(false);
        userFirstNameEdit.setClickable(false);
        userSecondNameEdit.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.input_disabled));
        userSecondNameEdit.setFocusable(false);
        userSecondNameEdit.setFocusableInTouchMode(false);
        userSecondNameEdit.setClickable(false);

        //Hide Register + Cancel Buttons
        Button cancelBtn = view.findViewById(R.id.register_cancel_btn);
        Button registerBtn = view.findViewById(R.id.register_submit_btn);
        cancelBtn.setVisibility(View.INVISIBLE);
        registerBtn.setVisibility(View.INVISIBLE);

        return view;
    }
}
