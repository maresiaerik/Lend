package android.project.lend;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExploreFragment extends Fragment implements Filter.OnFilterSelected {

    ProductManager productManager = new ProductManager();
    ListView listView = null;
    View view = null;
    ProductAdapter pa;
    ArrayList<ProductDataItem> productDataItemList;
    ArrayList<ProductDataItem> allItems = null;
    ArrayList<ProductDataItem> filteredList = null;
    Integer minPrice, maxPrice, minRating, maxRating;
    Filter filter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_explore, container, false);
        TextView pageTitle = view.findViewById(R.id.page_title);

        pageTitle.setText("Explore");
        playground();


        view.findViewById(R.id.filter_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("DEMO_LENGTH", productDataItemList.size() + "");
                filter = new Filter(allItems, view, listView, getContext());

                if(minRating != null && maxRating != null) {
                    filter.setRatingValues(minRating, maxRating);
                }
                if (minPrice != null && maxPrice != null) {
                    filter.setPriceValues(minPrice, maxPrice);
                }

                filter.show();
                filter.setFilterCallback(ExploreFragment.this);
            }
        });

        EditText sbar = view.findViewById(R.id.explore_search);

        TextWatcher txtWatch = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d("CHARS", s.length() + "");
                search(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        sbar.addTextChangedListener(txtWatch);
        return  view;

    }


    private void search(CharSequence word) {

        ArrayList<ProductDataItem> items = filteredList == null ? productDataItemList : filteredList;

        SearchBar searchBar = new SearchBar(items);
        ArrayList<ProductDataItem> searchedItems = searchBar.search(word);

        filteredList = word.length()  == 0 ? productDataItemList : searchedItems;


        updateView();

    }


    private void playground() {
        /*
            ArrayList<ProductDataItem> productDataItemList = new ArrayList<>();
        */
        productDataItemList = productManager.getDataList();
        allItems = productManager.getDataList();


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

    @Override
    public void filterSelected(ArrayList<ProductDataItem> items, Integer minPrice, Integer maxPrice, Integer minRating, Integer maxRating) {

        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRating = minRating;
        this.maxRating = maxRating;
        filteredList = items;
        productDataItemList = filteredList;
        updateView();

    }

    private void updateView() {

        ArrayList<ProductDataItem> items = filteredList == null ? productDataItemList : filteredList;
        pa = new ProductAdapter(view.getContext(), items);
        listView.setAdapter(pa);
    }

}