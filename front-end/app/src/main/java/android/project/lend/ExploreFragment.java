package android.project.lend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private int REL_SWIPE_MIN_DISTANCE;
    private int REL_SWIPE_MAX_OFF_PATH;
    private int REL_SWIPE_THRESHOLD_VELOCITY;
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

        DisplayMetrics dm = getResources().getDisplayMetrics();
        REL_SWIPE_MIN_DISTANCE = (int)(100.0f * dm.densityDpi / 160.0f + 0.5);
        REL_SWIPE_MAX_OFF_PATH = (int)(300.0f * dm.densityDpi / 160.0f + 0.5);
        REL_SWIPE_THRESHOLD_VELOCITY = (int)(200.0f * dm.densityDpi / 130.0f + 0.5);

        listView = view.findViewById(R.id.item_view);

        final GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }};
        listView.setOnTouchListener(gestureListener);

        final SwipeRefreshLayout sw = view.findViewById(R.id.explore_swipe_refresh);
        sw.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ExploreFragment()).commit();
                sw.setRefreshing(false);
            }
        });

        productManager = new ProductManager(this,null);

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

                Log.d("AllItems_LEngthg", allItems.size() + "");
                Log.d("ProductDATA_Length", productDataItemList.size() + "");
                //Log.d("FilterListLength", filteredList.size() + "");

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

    private void myOnItemClick(View view) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDetailedView(view.getId());
            }
        });
        Log.d("HERE", "Item Clicked "+view.getId());
    }

    private void onLTRFling() {

    }

    private void onRTLFling() {
        BottomNavigationItemView lendzBtn = getActivity().findViewById(R.id.navbar_lendz);
        lendzBtn.performClick();
        lendzBtn.setPressed(true);
        lendzBtn.invalidate();
        lendzBtn.setPressed(false);
        lendzBtn.invalidate();
    }


    private void search(CharSequence word) {

        ArrayList<ProductDataItem> items = filteredList == null ? productDataItemList : filteredList;

        SearchBar searchBar = new SearchBar(items);
        ArrayList<ProductDataItem> searchedItems = searchBar.search(word);

        filteredList = word.length() == 0 ? productDataItemList : searchedItems;

        updateView();

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
            if (allItems.get(i).getId().equals(itemId)) {
                selectedItem = allItems.get(i);
                break;
            }
        }
        if (selectedItem != null) {

            DetailedItemViewFragment detailedItemView = new DetailedItemViewFragment();

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
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        //Item Clicked Listener
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openDetailedView(view.getId());
                }
            });
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH)
                return false;
            if(e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {
                onRTLFling();
            }  else if (e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {
                onLTRFling();
            }
            return false;
        }

    }
}