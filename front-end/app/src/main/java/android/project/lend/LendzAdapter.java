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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LendzAdapter extends ArrayAdapter<LendzDataItem> {

    private Context context;
    private List<LendzDataItem> lendzList = new ArrayList<>();
    public LendzAdapter(@NonNull Context context, @LayoutRes ArrayList<LendzDataItem> list) {
        super(context, 0 , list);
        this.context = context;
        lendzList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.product_list_item,parent,false);

        LendzDataItem currentLendz = lendzList.get(position);

        listItem.setId(currentLendz.getId());

        TextView price = (TextView) listItem.findViewById(R.id.product_price);
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedPrice = df.format(currentLendz.product.getPrice());
        price.setText(formattedPrice + "€/day");

        TextView name = (TextView) listItem.findViewById(R.id.product_name);
        name.setText(currentLendz.product.getName());

        ImageView img = listItem.findViewById(R.id.imageView);

        if(currentLendz.product.imageDataItems.size() > 0) {

            new ImageDownloader(img).execute(currentLendz.product.imageDataItems.get(0).getUrl());

        } else {

            Drawable icon = context.getResources().getDrawable(R.drawable.gallery_icon);
            img.setImageDrawable(icon);
        }

        RatingBar rating = listItem.findViewById(R.id.product_rating);

        rating.setRating(currentLendz.product.getRating());

        return listItem;
    }
}
