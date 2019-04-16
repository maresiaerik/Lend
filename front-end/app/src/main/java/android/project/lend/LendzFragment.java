package android.project.lend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LendzFragment extends Fragment implements IDataController {

    private LendzManager lendzManager;
    private ArrayList<LendzDataItem> lendzDataItemList;

    private View view = null;
    private ListView listView = null;
    private LendzAdapter lendzAdapter;
    private int REL_SWIPE_MIN_DISTANCE;
    private int REL_SWIPE_MAX_OFF_PATH;
    private int REL_SWIPE_THRESHOLD_VELOCITY;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lendz, container, false);
        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText("Lendz");

        listView = (ListView) view.findViewById(R.id.item_view);

        final GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector());
        View.OnTouchListener gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
        listView.setOnTouchListener(gestureListener);

        lendzManager = new LendzManager(this, null);
        REL_SWIPE_MIN_DISTANCE = 120;
        REL_SWIPE_MAX_OFF_PATH = 250;
        REL_SWIPE_THRESHOLD_VELOCITY = 200;

        final SwipeRefreshLayout sw = view.findViewById(R.id.lendz_swipe_refresh);
        sw.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LendzFragment()).commit();
                sw.setRefreshing(false);
            }
        });

        return view;
    }

    private void onLTRFling() {
        BottomNavigationItemView exploreBtn = getActivity().findViewById(R.id.navbar_explore);
        exploreBtn.performClick();
        exploreBtn.setPressed(true);
        exploreBtn.invalidate();
        exploreBtn.setPressed(false);
        exploreBtn.invalidate();
    }

    private void onRTLFling() {
        BottomNavigationItemView homeBtn = getActivity().findViewById(R.id.navbar_home);
        homeBtn.performClick();
        homeBtn.setPressed(true);
        homeBtn.invalidate();
        homeBtn.setPressed(false);
        homeBtn.invalidate();
    }

    private void openReceipt(Integer id) {
        LendzDataItem lendItem = null;
        for (int i = 0; i < lendzDataItemList.size(); i++) {

            if (lendzDataItemList.get(i).getId().equals(id)) {
                lendItem = lendzDataItemList.get(i);
                break;
            }
        }
        Log.d("lendItem", lendItem +"");
        if (lendItem != null) {

            Intent receiptIntent = new Intent(getContext(), ExploreReceiptActivity.class);
            receiptIntent.putExtra("itemData", (Parcelable) lendItem.product);
            receiptIntent.putExtra("userEmail", lendItem.lender.getEmailAddress());
            receiptIntent.putExtra("userAddress", lendItem.lender.getHomeAddress());
            receiptIntent.putExtra("userPhone", lendItem.lender.getPhoneNumber());
            receiptIntent.putExtra("datesBtn", lendItem.getStartDate());
            receiptIntent.putExtra("endDate", lendItem.getDueDate());
            receiptIntent.putExtra("imageURL", lendItem.product.imageDataItems.get(0).getUrl());
            ActivityOptions options = ActivityOptions.makeCustomAnimation(getContext(), R.anim.in_bottom, R.anim.out_top);
            startActivity(receiptIntent, options.toBundle());

        }
    }

    @Override
    public void setData() {

        lendzDataItemList = new ArrayList<>();
        ArrayList<LendzDataItem> unratedLendzDataItems = lendzManager.getLendzListByUser(MainActivity.USER.getId());
        ArrayList<LendzDataItem> ratedLendzDataItems = lendzManager.getLendzListByRating(MainActivity.USER.getId());

        lendzDataItemList.addAll(unratedLendzDataItems);
        lendzDataItemList.addAll(ratedLendzDataItems);

        lendzAdapter = new LendzAdapter(view.getContext(), lendzDataItemList);
        listView.setAdapter(lendzAdapter);
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
                    openReceipt(view.getId());
                }
            });
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(e1.getY() - e2.getY()) > REL_SWIPE_MAX_OFF_PATH)
                return false;
            if (e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {
                onRTLFling();
            } else if (e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE &&
                    Math.abs(velocityX) > REL_SWIPE_THRESHOLD_VELOCITY) {
                onLTRFling();
            }
            return false;
        }

    }
}
