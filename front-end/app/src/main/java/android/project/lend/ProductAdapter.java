package android.project.lend;

import android.content.Context;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<ProductDataItem> {

    private Context context;
    private List<ProductDataItem> productList = new ArrayList<>();
    public ProductAdapter(@NonNull Context context, @LayoutRes ArrayList<ProductDataItem> list) {
        super(context, 0 , list);
        this.context = context;
        productList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.product_list_item,parent,false);

        ProductDataItem currentProduct = productList.get(position);

        TextView price = (TextView) listItem.findViewById(R.id.product_price);
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(currentProduct.getPrice());
        price.setText(formattedPrice + "€/day");

        TextView name = (TextView) listItem.findViewById(R.id.product_name);
        name.setText(currentProduct.getName());

        if(currentProduct.imageDataItems.size() > 0) {

            ImageView img = listItem.findViewById(R.id.imageView);
            new ImageDownloader(img).execute(currentProduct.imageDataItems.get(0).getUrl());
        }

        RatingBar rating = listItem.findViewById(R.id.product_rating);

        rating.setRating(currentProduct.getRating());

        return listItem;
    }
}
