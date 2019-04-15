package android.project.lend;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements IDataController {

    private ProductManager productManager;
    private  LendzManager lendzManager;
    private ArrayList<ProductDataItem> productDataItemList;
    private ArrayList<LendzDataItem> lendzDataItemList;
    Button addNewItemBtn;
    int scrolling;
    private View view = null;
    private ListView listView = null;
    private HomeAdapter homeAdapter;
    private int REL_SWIPE_MIN_DISTANCE;
    private int REL_SWIPE_MAX_OFF_PATH;
    private int REL_SWIPE_THRESHOLD_VELOCITY;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        lendzManager = new LendzManager(this);
        REL_SWIPE_MIN_DISTANCE =  120;
        REL_SWIPE_MAX_OFF_PATH = 250;
        REL_SWIPE_THRESHOLD_VELOCITY = 200;

        listView = view.findViewById(R.id.item_view);

        final GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        listView.setOnTouchListener(gestureListener);


        //Setting Page title
        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText("Home");

        final SwipeRefreshLayout sw = view.findViewById(R.id.home_swipe_refresh);
        sw.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                sw.setRefreshing(false);
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
                    addNewItemBtn.animate().translationY(-100).alpha(0).setDuration(200).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            addNewItemBtn.setVisibility(View.GONE);
                        }
                    });
                }
                if ((mLastFirstVisibleItem > firstVisibleItem && scrolling == 2) || firstVisibleItem == 0 && visibleItemCount == 2) {
                    addNewItemBtn.setVisibility(View.VISIBLE);
                    addNewItemBtn.animate().translationY(0).alpha(1).setDuration(200);
                }
                mLastFirstVisibleItem = firstVisibleItem;
            }
        });


        //Creating Home User Edit Fragment
        final Fragment editFragment = new HomeUserEditFragment();

        //User Edit Button Action
        final Button userEditBtn = view.findViewById(R.id.userEditBtn);

        if (MainActivity.USER != null) {
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
        addNewItemBtn = view.findViewById(R.id.addNewItemBtn);
        addNewItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, newItemFragment).commit();
            }
        });

        productManager = new ProductManager(this, null);


        return view;
    }

    private void myOnItemClick(int position) {
        String str = ("Item clicked = " + position);
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    private void onLTRFling() {
        BottomNavigationItemView lendzBtn = getActivity().findViewById(R.id.navbar_lendz);
        lendzBtn.performClick();
        lendzBtn.setPressed(true);
        lendzBtn.invalidate();
        lendzBtn.setPressed(false);
        lendzBtn.invalidate();
    }

    @Override
    public void setData() {
        productDataItemList = productManager.getHomeProductList(MainActivity.USER.getId());
        homeAdapter = new HomeAdapter(view.getContext(), productDataItemList);
        listView.setAdapter(homeAdapter);
    }

    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        //Item Clicked Listener
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            ListView lv = listView;
            int pos = lv.pointToPosition((int) e.getX(), (int) e.getY());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    openDialog(view.getId());
                }
            });
            myOnItemClick(pos);

            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH)
                return false;
            if (e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {
                onLTRFling();
            }
            return false;
        }

    }

    private void openDialog(final Integer id) {
        final Dialog itemDialog = new Dialog(getContext());
        itemDialog.setContentView(R.layout.picture_input_layout);
        ImageView calendarIcon = itemDialog.findViewById(R.id.imageView2);
        ImageView editIcon = itemDialog.findViewById(R.id.message_icon);
        TextView editText = itemDialog.findViewById(R.id.textView);
        TextView calendarText = itemDialog.findViewById(R.id.textView2);

        editText.setText("Edit product");
        calendarText.setText("See Calendar");

        calendarIcon.setImageResource(R.drawable.edit_icon);
        editIcon.setImageResource(R.drawable.calendar_icon);
        itemDialog.show();
        //Open Edit item view
        itemDialog.findViewById(R.id.contact_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemDialog.dismiss();
            }

        });
        //Open Calendar
        itemDialog.findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar(id);
                itemDialog.dismiss();
            }
        });
    }

    private void openCalendar(Integer id) {
        HomeStatisticsFragment homeStatisticsFragment = new HomeStatisticsFragment();

        lendzDataItemList = lendzManager.getLendzListByProduct(id);

        ProductDataItem selectedItem = null;
        for (int i = 0; i < productDataItemList.size(); i++) {

            if(productDataItemList.get(i).getId().equals(id)) {
                selectedItem = productDataItemList.get(i);
                break;
            }
        }

        if(selectedItem != null ) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedItem", selectedItem);
            bundle.putSerializable("selectedItemLendz", lendzDataItemList);
            homeStatisticsFragment.setArguments(bundle);
            FragmentManager manager = getActivity().getSupportFragmentManager();
            manager.popBackStack();
            FragmentTransaction ft = manager.beginTransaction();
            ft.setCustomAnimations(R.anim.in_right, R.anim.out_left);
            ft.replace(R.id.fragment_container, homeStatisticsFragment);
            ft.addToBackStack(null);
            ft.commit();

        }
    }


}





