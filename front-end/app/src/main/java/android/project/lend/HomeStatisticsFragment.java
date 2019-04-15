package android.project.lend;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeStatisticsFragment extends Fragment {

    ProductDataItem productDataItem;
    ArrayList<LendzDataItem> lendzDataItem;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home_statistics_fragment, container, false);
        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText("Statistics");

        Bundle args = getArguments();
        productDataItem = (ProductDataItem) args.getSerializable("selectedItem");
        lendzDataItem = (ArrayList<LendzDataItem>) args.getSerializable("selectedItemLendz");

        for (int i = 0; i < lendzDataItem.size(); i++) {
            Log.d("here", lendzDataItem.get(i).getDueDate());
        }

        return view;
    }
}
