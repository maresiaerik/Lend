package android.project.lend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LendzFragment extends Fragment implements IDataController {

    private LendzManager lendzManager;
    private ArrayList<LendzDataItem> lendzDataItemList;

    private View view = null;
    private ListView listView = null;
    private LendzAdapter lendzAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_lendz, container, false);
        TextView pageTitle = view.findViewById(R.id.page_title);
        pageTitle.setText("Lendz");

        listView = (ListView) view.findViewById(R.id.item_view);

        lendzManager = new LendzManager(this, null);

        return view;
    }

    @Override
    public void setData() {

        lendzDataItemList = lendzManager.getLendzList(MainActivity.USER.getId());

        lendzAdapter = new LendzAdapter(view.getContext(), lendzDataItemList);
        listView.setAdapter(lendzAdapter);
    }
}
