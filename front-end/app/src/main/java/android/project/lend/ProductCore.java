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
    private boolean changed_lendzId;
    private boolean changed_name;
    private boolean changed_price;
    private boolean changed_rating;
    private boolean changed_image;
    private boolean changed_description;
    private boolean changed_category;


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
        changed_lendzId = true;

        this.lendzId = lendzId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {

        if (name == this.name) return;

        changed = true;
        changed_name = true;

        this.name = name;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {

        if (price == this.price) return;

        changed = true;
        changed_price = true;

        this.price = price;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {

        if (rating == this.rating) return;

        changed = true;
        changed_rating = true;

        this.rating = rating;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {

        if (image == this.image) return;

        changed = true;
        changed_image = true;

        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (description == this.description) return;

        changed = true;
        changed_image = true;

        this.description = description;

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {

        if (category == this.category) return;

        changed = true;
        changed_category = true;

        this.category = category;
    }


    public void create() {

    }

    public void update() {
        if (!changed) return;

        Log.d("PRODUCTCHANGE", "Something was changed!");
        if (changed_lendzId) {
            Log.d("PRODUCTCHANGED", "LendzID was changed to " + this.lendzId);
        }

        if (changed_name) {
            Log.d("PRODUCTCHANGED", "Name was changed to " + this.name);
        }
        if (changed_price) {
            Log.d("PRODUCTCHANGED", "Price was changed to " + this.price);
        }
        if (changed_rating) {
            Log.d("PRODUCTCHANGED", "Rating was changed to " + this.rating);
        }
        if (changed_image) {
            Log.d("PRODUCTCHANGED", "Image was changed to " + this.image);
        }
        if (changed_description) {
            Log.d("PRODUCTCHANGED", "Description was changed to " + this.description);
        }
        if (changed_category) {
            Log.d("PRODUCTCHANGED", "Category was changed to " + this.category);
        }


        clearChanges();
    }

    public void delete() {
        Log.d("PRODUCTDELETED", this.name + " was deleted");
    }

    public void clearChanges() {
        changed = false;
        changed_lendzId = false;
        changed_name = false;
        changed_price = false;
        changed_rating = false;
        changed_image = false;
        changed_description = false;
        changed_category = false;

    }


}
