package android.project.lend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class Filter{

    interface OnFilterSelected {
        void filterSelected(ArrayList<ProductDataItem> items, Integer minPrice, Integer maxPrice, Integer minRating, Integer maxRating);
    }

    private ArrayList<ProductDataItem> productDataItem = null;
    private ArrayList<ProductDataItem> filteredList = new ArrayList<>();
    private View root;
    private RangeSeekBar<Integer> seekBar, ratingsBar;
    private Integer maxPrice, minPrice;
    private Dialog dialog;
    private Integer minRating, maxRating;
    private Integer oldMinPrice = 0, oldMaxPrice = 1000;
    private Integer oldMinRating = 0, oldMaxRating = 5;
    private ListView listView;
    private Context context ;
    private ArrayList<Object> list = new ArrayList<>();

    private OnFilterSelected filterCallback;

    public void setFilterCallback(OnFilterSelected filterCallback) {
        this.filterCallback = filterCallback;
    }

    public Filter(ArrayList<ProductDataItem> productDataItem, View view, ListView listview, Context ctx ) {
        root = view;
        this.productDataItem = productDataItem;
        this.listView = listview;
        this.context = ctx;

        dialog = new Dialog(root.getContext());
        dialog.setContentView(R.layout.filter_dialog);
        seekBar = dialog.findViewById(R.id.filter_range_seekbar);
        ratingsBar = dialog.findViewById(R.id.filter_ratings_seekbar);
        dialog.findViewById(R.id.filter_accept_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                maxPrice = seekBar.getSelectedMaxValue();
                minPrice = seekBar.getSelectedMinValue();

                minRating = ratingsBar.getSelectedMinValue();
                maxRating = ratingsBar.getSelectedMaxValue();

                filter();
            }
        });
    }

    public void setRatingValues(Integer min, Integer max) {
        this.oldMinRating = min;
        this.oldMaxRating = max;
    }
    public void setPriceValues(Integer min, Integer max) {
        this.oldMinPrice = min;
        this.oldMaxPrice = max;
    }
    public void show() {

        setValues();
        dialog.show();
    }

    private void setValues() {

        seekBar.setSelectedMinValue(this.oldMinPrice);
        seekBar.setSelectedMaxValue(this.oldMaxPrice);

        ratingsBar.setSelectedMinValue(this.oldMinRating);
        ratingsBar.setSelectedMaxValue(this.oldMaxRating);
    }

    private void filter() {

        for (int i = 0; i < productDataItem.size() ; i++) {

            if(
                    productDataItem.get(i).getPrice() >= minPrice && productDataItem.get(i).getPrice() <= maxPrice &&
                    productDataItem.get(i).getRating() >= minRating && productDataItem.get(i).getRating() <= maxRating
            ) {

                filteredList.add(productDataItem.get(i));
            }
        }

        if (filterCallback != null) {
            filterCallback.filterSelected(filteredList, minPrice, maxPrice, minRating, maxRating);
        }

        dialog.dismiss();

    }

}



