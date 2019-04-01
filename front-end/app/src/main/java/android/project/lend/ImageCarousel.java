package android.project.lend;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageCarousel extends PagerAdapter {

    private Context ctx;
    private ArrayList<String> imageIds;
    private ProductDataItem item;

    public ImageCarousel(Context c, ProductDataItem item) {
        this.ctx = c;
        this.item = item;
        imageIds = item.getAllImages();
    }

    @Override
    public int getCount() {
        return imageIds.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(ctx);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        new ImageDownloader(imageView).execute(imageIds.get(position));

        container.addView(imageView, 0);

        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
