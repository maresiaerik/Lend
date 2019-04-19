package android.project.lend;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductCore implements Serializable, Parcelable {

    private Integer id;
    private Integer userId;
    private Integer lendzId;
    private String name;
    private Float price;
    private Integer rating;
    private String description;
    private String category;
    private Integer status;

    private Boolean changed = false;
    private Boolean changedLendzId = false;
    private Boolean changedName = false;
    private Boolean changedPrice = false;
    private Boolean changedRating = false;
    private Boolean changedDescription = false;
    private Boolean changedCategory = false;
    private Boolean changedStatus = false;

    protected ProductCore(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readInt();
        }
        if (in.readByte() == 0) {
            lendzId = null;
        } else {
            lendzId = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readFloat();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readInt();
        }
        description = in.readString();
        category = in.readString();
        changed = in.readByte() != 0;
        changedLendzId = in.readByte() != 0;
        changedName = in.readByte() != 0;
        changedPrice = in.readByte() != 0;
        changedRating = in.readByte() != 0;
        changedDescription = in.readByte() != 0;
        changedCategory = in.readByte() != 0;
        changedStatus = in.readByte() != 0;
    }

    public static final Creator<ProductCore> CREATOR = new Creator<ProductCore>() {
        @Override
        public ProductCore createFromParcel(Parcel in) {
            return new ProductCore(in);
        }

        @Override
        public ProductCore[] newArray(int size) {
            return new ProductCore[size];
        }
    };

    public ProductCore() {


    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLendzId() {
        return lendzId;
    }

    public void setLendzId(Integer lendzId) {

        if (lendzId == this.lendzId) return;

        changed = true;
        changedLendzId = true;

        this.lendzId = lendzId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {

        if (name == this.name) return;

        changed = true;
        changedName = true;

        this.name = name;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {

        if (price == this.price) return;

        changed = true;
        changedPrice = true;

        this.price = price;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {

        if (rating == this.rating) return;

        changed = true;
        changedRating = true;

        this.rating = rating;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (description == this.description) return;

        changed = true;
        changedDescription = true;

        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {

        if (category == this.category) return;

        changed = true;
        changedCategory = true;

        this.category = category;
    }

    public Integer getStatus(){
        return status;
    }

    public void setStatus(Integer status) {

        if(status == this.status) return;

        changed = true;
        changedStatus = true;

        this.status = status;
    }

    public void create() {

    }

    public void update() {
        if (!changed) return;

        if (changedLendzId) {
            Log.d("PRODUCTCHANGED", "LendzID was changed to " + this.lendzId);
        }

        if (changedName) {
            Log.d("PRODUCTCHANGED", "Name was changed to " + this.name);
        }
        if (changedPrice) {
            Log.d("PRODUCTCHANGED", "Price was changed to " + this.price);
        }
        if (changedRating) {
            Log.d("PRODUCTCHANGED", "Rating was changed to " + this.rating);
        }
        if (changedDescription) {
            Log.d("PRODUCTCHANGED", "Description was changed to " + this.description);
        }
        if (changedCategory) {
            Log.d("PRODUCTCHANGED", "Category was changed to " + this.category);
        }
        if(changedStatus){}

        clearChanges();
    }

    public void delete() {
        Log.d("PRODUCTDELETED", this.name + " was deleted");
    }

    public void clearChanges() {
        changed = false;

        changedLendzId = false;
        changedName = false;
        changedPrice = false;
        changedRating = false;
        changedDescription = false;
        changedCategory = false;
        changedStatus = false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userId);
        }
        if (lendzId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(lendzId);
        }
        dest.writeString(name);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(price);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rating);
        }
        dest.writeString(description);
        dest.writeString(category);
        dest.writeByte((byte) (changed ? 1 : 0));
        dest.writeByte((byte) (changedLendzId ? 1 : 0));
        dest.writeByte((byte) (changedName ? 1 : 0));
        dest.writeByte((byte) (changedPrice ? 1 : 0));
        dest.writeByte((byte) (changedRating ? 1 : 0));
        dest.writeByte((byte) (changedDescription ? 1 : 0));
        dest.writeByte((byte) (changedCategory ? 1 : 0));
        dest.writeByte((byte) (changedStatus ? 1: 0));
    }
}
