package android.project.lend;

import android.util.Log;

import java.text.DecimalFormat;

public class ProductCore
{
    private Integer id;
    private String name;
    private float price;
    private Integer rating;
    private String image;
    private boolean changed;
    private boolean changed_name;
    private boolean changed_price;
    private boolean changed_rating;
    private boolean changed_image;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {

        if(name == this.name) return;

        changed = true;
        changed_name = true;

        this.name = name;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {

        if(price == this.price) return;

        changed = true;
        changed_price = true;

        this.price = price;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {

        if(rating == this.rating) return;

        changed = true;
        changed_rating = true;

        this.rating = rating;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {

        if(image == this.image) return;

        changed = true;
        changed_image = true;

        this.image = image;
    }

    public void create()
    {

    }

    public void update()
    {
        if(!changed) return;

        Log.d("PRODUCTCHANGE", "Something was changed!");

        if(changed_name){Log.d("PRODUCTCHANGED", "Name was changed to " + this.name);}
        if(changed_price){Log.d("PRODUCTCHANGED", "Price was changed to " + this.price);}
        if(changed_rating){Log.d("PRODUCTCHANGED", "Rating was changed to " + this.rating);}
        if(changed_image){Log.d("PRODUCTCHANGED", "Image was changed to " + this.image);}

        clearChanges();
    }

    public void delete()
    {
        Log.d("PRODUCTDELETED", this.name + " was deleted");
    }

    public void clearChanges()
    {
        changed = false;
        changed_name = false;
        changed_price = false;
        changed_rating = false;
        changed_image = false;
    }
}
