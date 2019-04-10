package android.project.lend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageCarousel extends PagerAdapter {

    private Context context;
    private ArrayList<ImageDataItem> imageDataItems;
    private ProductDataItem productDataItem;

    public ImageCarousel(Context context, ProductDataItem productDataItem) {
        this.context = context;
        this.productDataItem = productDataItem;
        imageDataItems = productDataItem.imageDataItems;
    }

    @Override
    public int getCount() {
        return imageDataItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        new ImageDownloader(imageView).execute(imageDataItems.get(position).getUrl());
        //imageView.setImageDrawable(imageDataItems.get(0).image);
        container.addView(imageView, 0);

        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
