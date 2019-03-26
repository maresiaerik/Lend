package android.project.lend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    ProductManager productManager = new ProductManager();
    ListView listView = null;
    View view = null;
    ProductAdapter pa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_explore, container, false);
        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText("Explore");
        playground();

        EditText sbar = view.findViewById(R.id.explore_search);

        TextWatcher filter = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        sbar.addTextChangedListener(filter);
        return  view;

    }

    private void filter(String word) {
        SearchBar searchBar = new SearchBar(productManager);
        ArrayList<ProductDataItem> searchedItems = searchBar.search(word);

        pa = new ProductAdapter(view.getContext(), searchedItems);
        listView.setAdapter(pa);
    }


    private void playground() {
        ArrayList<ProductDataItem> productDataItemList = new ArrayList<>();

        productDataItemList = productManager.getDataList();

        productDataItemList.get(0).setName("New name");
        productDataItemList.get(0).update();

        productDataItemList.get(2).setName("John");
        productDataItemList.get(2).update();

        productDataItemList.get(5).setRating(5);
        productDataItemList.get(5).setName("Just name");
        productDataItemList.get(5).update();

        productDataItemList.get(3).delete();

        listView = (ListView) view.findViewById(R.id.item_view);

        pa = new ProductAdapter(view.getContext(), productDataItemList);

        listView.setAdapter(pa);
    }
}