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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ExploreFragment extends Fragment implements Filter.OnFilterSelected, IDataController {

    private ProductManager productManager = null;
    private ArrayList<ProductDataItem> productDataItemList;

    private View view = null;
    private ListView listView = null;
    private ExploreAdapter exploreAdapter;

    private ArrayList<ProductDataItem> allItems = null;
    private ArrayList<ProductDataItem> filteredList = null;
    private Integer minPrice, maxPrice, minRating, maxRating, categoryPosition, sortByPosition;
    private Filter filter;

    private ProductDataItem selectedItem = null;

    Button filterBtn;
    EditText sbar;
    int scrolling;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_explore, container, false);
        listView = view.findViewById(R.id.item_view);
        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText("Explore");

        listView = (ListView) view.findViewById(R.id.item_view);

        productManager = new ProductManager(this,null);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                openDetailedView(view.getId());
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                scrolling = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mLastFirstVisibleItem < firstVisibleItem && scrolling == 2) {
                    filterBtn.animate().translationY(-100).alpha(0).setDuration(200).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            filterBtn.setVisibility(View.GONE);
                        }
                    });
                    sbar.animate().translationY(-100).alpha(0).setDuration(200).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            sbar.setVisibility(View.GONE);
                        }
                    });
                }
                if ((mLastFirstVisibleItem > firstVisibleItem && scrolling == 2) || firstVisibleItem == 0 && visibleItemCount == 2) {
                    filterBtn.setVisibility(View.VISIBLE);
                    filterBtn.animate().translationY(0).alpha(1).setDuration(200);
                    sbar.setVisibility(View.VISIBLE);
                    sbar.animate().translationY(0).alpha(1).setDuration(200);
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });
        filterBtn = view.findViewById(R.id.filter_button);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filter = new Filter(allItems, view, listView, getContext());

                if (minRating != null && maxRating != null) {
                    filter.setRatingValues(minRating, maxRating);
                }
                if (minPrice != null && maxPrice != null) {
                    filter.setPriceValues(minPrice, maxPrice);
                }
                if (categoryPosition != null) {
                    filter.setCategory(categoryPosition);
                }
                if (sortByPosition != null) {
                    filter.setSortBy(sortByPosition);
                }
                Log.d("HERE", ""+allItems.size());
                filter.show();
                filter.setFilterCallback(ExploreFragment.this);
            }
        });

        sbar = view.findViewById(R.id.explore_search);

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
        return view;

    }


    private void search(CharSequence word) {

        ArrayList<ProductDataItem> items = filteredList == null ? productDataItemList : filteredList;

        SearchBar searchBar = new SearchBar(items);
        ArrayList<ProductDataItem> searchedItems = searchBar.search(word);

        filteredList = word.length() == 0 ? productDataItemList : searchedItems;

        updateView();

    }


    private void playground() {
        /*
            ArrayList<ProductDataItem> productDataItemList = new ArrayList<>();
        */

        productDataItemList.get(0).setName("New name");
        productDataItemList.get(0).update();

        productDataItemList.get(2).setName("John");
        productDataItemList.get(2).update();

        productDataItemList.get(3).setRating(5);
        productDataItemList.get(3).setName("Just name");
        productDataItemList.get(3).update();
    }

    @Override
    public void filterSelected(ArrayList<ProductDataItem> items, Integer minPrice, Integer maxPrice, Integer minRating, Integer maxRating, Integer catIndex, Integer sortByIndex) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRating = minRating;
        this.maxRating = maxRating;
        this.categoryPosition = catIndex;
        this.sortByPosition = sortByIndex;
        filteredList = items;
        productDataItemList = filteredList;
        updateView();
    }

    public void updateView() {

        ArrayList<ProductDataItem> items = filteredList == null ? productDataItemList : filteredList;
        exploreAdapter = new ExploreAdapter(view.getContext(), items);
        listView.setAdapter(exploreAdapter);
    }

    private void openDetailedView(Integer itemId) {

        ProductDataItem selectedItem = null;

        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getId() == itemId) {
                selectedItem = allItems.get(i);
                break;
            }
        }
        if (selectedItem != null) {

            DetailedItemView detailedItemView = new DetailedItemView();

            Bundle bundle = new Bundle();

            bundle.putSerializable("selectedItem", selectedItem);
            detailedItemView.setArguments(bundle);
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, detailedItemView).commit();
        }
    }

    @Override
    public void setData() {

        productDataItemList = productManager.getExploreProductList(MainActivity.USER);
        allItems = productManager.getExploreProductList(MainActivity.USER);

        ArrayList<ProductDataItem> items = filteredList == null ? productDataItemList : filteredList;
        exploreAdapter = new ExploreAdapter(view.getContext(), items);
        listView.setAdapter(exploreAdapter);
    }
}