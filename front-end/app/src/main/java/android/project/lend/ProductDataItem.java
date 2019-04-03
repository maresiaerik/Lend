package android.project.lend;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDataItem extends ProductCore implements Serializable, Parcelable {

    public ArrayList<ImageDataItem> imageDataItems = new ArrayList<>();
    public UserDataItem owner = new UserDataItem();

    protected ProductDataItem(Parcel in) {
    }

    public static final Creator<ProductDataItem> CREATOR = new Creator<ProductDataItem>() {
        @Override
        public ProductDataItem createFromParcel(Parcel in) {
            return new ProductDataItem(in);
        }

        @Override
        public ProductDataItem[] newArray(int size) {
            return new ProductDataItem[size];
        }
    };

    public ProductDataItem() {
        
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
