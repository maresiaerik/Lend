package android.project.lend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeUserEditFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_user_edit, container, false);

        //Setting Image
        ImageView img = view.findViewById(R.id.userProfilePicEdit);
        new ImageDownloader(img).execute(MainActivity.USER.getImageUrl());

        //Setting First Name
        TextView userFirstNameEdit = view.findViewById(R.id.userFirstNameEdit);
        userFirstNameEdit.setText(MainActivity.USER.getFirstName());

        //Setting Second Name
        TextView userSecondNameEdit = view.findViewById(R.id.userSecondNameEdit);
        userSecondNameEdit.setText(MainActivity.USER.getLastName());

        //Setting Email
        final TextView userEmailEdit = view.findViewById(R.id.userEmailEdit);
        userEmailEdit.setText(MainActivity.USER.getEmailAddress());

        //Setting Phone
        TextView userPhoneEdit = view.findViewById(R.id.userPhoneEdit);
        userPhoneEdit.setText(MainActivity.USER.getPhoneNumber());

        //Setting Email
        TextView userAddressEdit = view.findViewById(R.id.userAddressEdit);
        userAddressEdit.setText(MainActivity.USER.getHomeAddress());

        //Setting Credit Card
        TextView userCardEdit = view.findViewById(R.id.userCardEdit);
        userCardEdit.setText(MainActivity.USER.getCardNumber());

        return view;
    }
}
