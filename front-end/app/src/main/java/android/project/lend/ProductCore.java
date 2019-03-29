package android.project.lend;

import android.util.Log;

public class ProductCore {
    private Integer id;
    private Integer userId;
    private Integer lendzId;
    private String name;
    private Float price;
    private Integer rating;
    private String image;
    private String description;
    private String category;
    private boolean changed;
    private boolean changedLendzId;
    private boolean changedName;
    private boolean changedPrice;
    private boolean changedRating;
    private boolean changedImage;
    private boolean changedDescription;
    private boolean changedCategory;

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

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {

        if (image == this.image) return;

        changed = true;
        changedImage = true;

        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (description == this.description) return;

        changed = true;
        changedImage = true;

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


    public void create() {

    }

    public void update() {
        if (!changed) return;

        Log.d("PRODUCTCHANGE", "Something was changed!");
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
        if (changedImage) {
            Log.d("PRODUCTCHANGED", "Image was changed to " + this.image);
        }
        if (changedDescription) {
            Log.d("PRODUCTCHANGED", "Description was changed to " + this.description);
        }
        if (changedCategory) {
            Log.d("PRODUCTCHANGED", "Category was changed to " + this.category);
        }

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
        changedImage = false;
        changedDescription = false;
        changedCategory = false;
    }
}
