package android.project.lend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LendzFragment extends Fragment implements IDataController {

    private LendzManager lendzManager;
    private ArrayList<LendzDataItem> lendzDataItemList;
    static public ArrayList<LendzDataItem> allLendzDataItemList;

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
            }};
        listView.setOnTouchListener(gestureListener);

        lendzManager = new LendzManager(this, null);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        REL_SWIPE_MIN_DISTANCE = (int)(100.0f * dm.densityDpi / 160.0f + 0.5);
        REL_SWIPE_MAX_OFF_PATH = (int)(300.0f * dm.densityDpi / 160.0f + 0.5);
        REL_SWIPE_THRESHOLD_VELOCITY = (int)(150.0f * dm.densityDpi / 130.0f + 0.5);


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

    private void myOnItemClick(int position) {
        String str = ("Item clicked = "+position);
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
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

    @Override
    public void setData() {
        lendzDataItemList = lendzManager.getLendzListByUser(MainActivity.USER.getId());
        lendzAdapter = new LendzAdapter(view.getContext(), lendzDataItemList);
        listView.setAdapter(lendzAdapter);
    }
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
        //Item Clicked Listener
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            ListView lv = listView;
            int pos = lv.pointToPosition((int)e.getX(), (int)e.getY());
            myOnItemClick(pos);
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
