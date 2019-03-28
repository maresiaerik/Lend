package android.project.lend;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    View view;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Setting Page title
        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText("Home");

        //Creating Home User Edit Fragment
        final Fragment editFragment = new HomeUserEditFragment();

        //User Edit Button Action
        final Button userEditBtn = view.findViewById(R.id.userEditBtn);
        userEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, editFragment).commit();
            }
        });

        //Creating New Item Fragment
        final Fragment newItemFragment = new HomeNewItemFragment();

        //Add Item Button Action
        final Button addNewItem = view.findViewById(R.id.addNewItemBtn);
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, newItemFragment).commit();
            }
        });

        addNewItem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.button_submit);
                    addNewItem.setTextColor(getResources().getColor(R.color.submitColour));
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.button_submit_pressed);
                    addNewItem.setTextColor(getResources().getColor(R.color.whiteColour));

                }

                return false;
            }
        });
        userEditBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.button_accent_pressed);
                    userEditBtn.setTextColor(getResources().getColor(R.color.whiteColour));
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.button_accent);
                    userEditBtn.setTextColor(getResources().getColor(R.color.colorAccent));
                }

                return false;
            }
        });

        return view;

    }

}
