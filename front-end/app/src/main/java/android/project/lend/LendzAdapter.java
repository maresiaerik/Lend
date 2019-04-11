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

        TextView name = (TextView) listItem.findViewById(R.id.product_name);
        name.setText(currentLendz.product.getName());

        TextView dueDate = (TextView) listItem.findViewById(R.id.product_status);
        dueDate.setText(currentLendz.getDueDate());

        ImageView img = listItem.findViewById(R.id.imageView);

        if(currentLendz.product.imageDataItems.size() > 0) {

            Glide.with(listItem).load(currentLendz.product.imageDataItems.get(0).getUrl()).into(img);

        } else {

            Drawable icon = context.getResources().getDrawable(R.drawable.gallery_icon);
            img.setImageDrawable(icon);
        }

        RatingBar rating = listItem.findViewById(R.id.product_rating);
        rating.setVisibility(View.GONE);

        return listItem;
    }
}
