package android.project.lend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LendzFragment extends Fragment {

    View view;

    ListView listView = null;
    LendzAdapter lendzAdapter;

    LendzManager lendzManager = new LendzManager();
    private ArrayList<LendzDataItem> lendzDataItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lendz, container, false);
        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText("Lendz");

        lendzDataItemList = lendzManager.getLendzList();

        listView = (ListView) view.findViewById(R.id.item_view);
        lendzAdapter = new LendzAdapter(view.getContext(), lendzDataItemList);
        listView.setAdapter(lendzAdapter);

        return view;
    }
}
