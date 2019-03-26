package android.project.lend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeUserEditFragment extends Fragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_user_edit, container, false);

        //Setting First Name
        TextView userFirstNameEdit = view.findViewById(R.id.userFirstNameEdit);
        userFirstNameEdit.setText("Tester");

        //Setting Second Name
        TextView userSecondNameEdit = view.findViewById(R.id.userSecondNameEdit);
        userSecondNameEdit.setText("Userman");

        //Setting Email
        TextView userEmailEdit = view.findViewById(R.id.userEmailEdit);
        userEmailEdit.setText("testUser@email.com");

        //Setting Phone
        TextView userPhoneEdit = view.findViewById(R.id.userPhoneEdit);
        userPhoneEdit.setText("+35898238457");

        //Setting Email
        TextView userAddressEdit = view.findViewById(R.id.userAddressEdit);
        userAddressEdit.setText("1 Road Street, Oulu, 9000");

        //Setting Credit Card
        TextView userCardEdit = view.findViewById(R.id.userCardEdit);
        userCardEdit.setText("4444 4565 3333 5234");

        return view;

    }
}
