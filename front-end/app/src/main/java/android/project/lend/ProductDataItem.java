package android.project.lend;

import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDataItem extends ProductCore implements Serializable {
    public ArrayList<ImageDataItem> imageDataItems = new ArrayList<>();

    protected ProductDataItem(Parcel in) {
        super(in);
    }

    public ProductDataItem() {
        super();
    }
}
