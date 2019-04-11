package android.project.lend;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements IDataController {

    private ProductManager productManager;
    private ArrayList<ProductDataItem> productDataItemList;

    private View view = null;
    private ListView listView = null;
    private HomeAdapter homeAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Setting Page title
        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText("Home");

        listView = (ListView) view.findViewById(R.id.item_view);

        //Creating Home User Edit Fragment
        final Fragment editFragment = new HomeUserEditFragment();

        //User Edit Button Action
        final Button userEditBtn = view.findViewById(R.id.userEditBtn);

        if(MainActivity.USER != null)
        {
            userEditBtn.setText(MainActivity.USER.getFirstName());

            userEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, editFragment).commit();
                }
            });

        } else {

        }

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

        productManager = new ProductManager(this,null);

        return view;
    }

    @Override
    public void setData() {

        productDataItemList = productManager.getHomeProductList(MainActivity.USER.getId());

        homeAdapter = new HomeAdapter(view.getContext(), productDataItemList);
        listView.setAdapter(homeAdapter);
    }
}
