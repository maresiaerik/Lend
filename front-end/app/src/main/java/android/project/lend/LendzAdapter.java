package android.project.lend;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LendzAdapter extends ArrayAdapter<LendzDataItem> {

    private Context context;
    private View listItem;
    private List<LendzDataItem> lendzList = new ArrayList<>();

    public LendzAdapter(@NonNull Context context, ArrayList<LendzDataItem> list) {
        super(context, 0, list);
        this.context = context;
        lendzList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);

        final LendzDataItem currentLendz = lendzList.get(position);
        listItem.setId(currentLendz.getId());

        TextView name = (TextView) listItem.findViewById(R.id.product_name);
        name.setText(currentLendz.product.getName());

        final TextView dueDate = (TextView) listItem.findViewById(R.id.product_status);

        ImageView img = listItem.findViewById(R.id.imageView);
        final RatingBar userRating = listItem.findViewById(R.id.user_product_rating);
        img.setAlpha((float) 1.0);
        userRating.setVisibility(View.GONE);

        if (hasDueDatePassed(currentLendz)) {
            dueDate.setText("Rate product");
            img.setAlpha((float) 0.2);
            userRating.setStepSize(1);
            userRating.setVisibility(View.VISIBLE);
            if (currentLendz.getRating() == null) {
                userRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        currentLendz.uploadRating(rating, listItem);
                        userRating.setIsIndicator(true);
                        dueDate.setText("Returned");
                    }
                });
            } else {
                userRating.setIsIndicator(true);
                userRating.setVisibility(View.GONE);
                dueDate.setText("Returned");
                userRating.setAlpha((float) 0.9);
            }
        } else {
            dueDate.setText(currentLendz.getDueDate());
        }

        if (currentLendz.product.imageDataItems.size() > 0) {

            Glide.with(listItem).load(currentLendz.product.imageDataItems.get(0).getUrl()).into(img);

        } else {

            Drawable icon = context.getResources().getDrawable(R.drawable.gallery_icon);
            img.setImageDrawable(icon);
        }

        RatingBar rating = listItem.findViewById(R.id.product_rating);
        rating.setVisibility(View.GONE);

        return listItem;
    }

    private Boolean hasDueDatePassed(LendzDataItem currentLendz) {
        Calendar endDate = Calendar.getInstance(), now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            endDate.setTime(sdf.parse(currentLendz.getDueDate()));
        } catch (ParseException e) {
            Log.d("dateTimeError", e + "");
        }
        //if duedate is in the future or already gone
        return endDate.after(now) ? false : true;
    }
}