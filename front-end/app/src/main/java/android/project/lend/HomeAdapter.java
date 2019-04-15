package android.project.lend;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends ArrayAdapter<ProductDataItem> {

    private Context context;
    private List<ProductDataItem> productList = new ArrayList<>();

    public HomeAdapter(@NonNull Context context, @LayoutRes ArrayList<ProductDataItem> list) {
        super(context, 0, list);
        this.context = context;
        productList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);

        ProductDataItem currentProduct = productList.get(position);

        listItem.setId(currentProduct.getId());

        TextView name = (TextView) listItem.findViewById(R.id.product_name);
        name.setText(currentProduct.getName());

        //TextView status = (TextView) listItem.findViewById(R.id.product_status);
        //status.setText(MainActivity.PRODUCT_STATUS[currentProduct.getStatus()]);


        ImageView img = listItem.findViewById(R.id.imageView);

        if (currentProduct.imageDataItems.size() > 0) {

            Glide.with(listItem).load(currentProduct.imageDataItems.get(0).getUrl()).into(img);

        } else {

            Drawable icon = context.getResources().getDrawable(R.drawable.gallery_icon);
            img.setImageDrawable(icon);
        }

        RatingBar rating = listItem.findViewById(R.id.product_rating);
        rating.setVisibility(View.GONE);

        return listItem;
    }
}
