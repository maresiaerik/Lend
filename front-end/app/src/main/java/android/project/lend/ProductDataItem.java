package android.project.lend;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDataItem extends ProductCore implements Serializable, Parcelable {

    public ArrayList<ImageDataItem> imageDataItems = new ArrayList<>();
    public UserDataItem owner = new UserDataItem();

    protected ProductDataItem(Parcel in) {
        super(in);
    }

    public ProductDataItem() {
        super();
    }
}
