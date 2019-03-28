package android.project.lend;

import android.util.Log;

public class ProductCore
{
    private Integer id;
    private String name;
    private Float price;
    private Integer rating;
    private String image;

    private Boolean changed;
    private Boolean changedName;
    private Boolean changedPrice;
    private Boolean changedRating;
    private Boolean changedImage;

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
        changedName = true;

        this.name = name;
    }

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {

        if(price == this.price) return;

        changed = true;
        changedPrice = true;

        this.price = price;
    }

    public Integer getRating() {
        return this.rating;
    }

    public void setRating(Integer rating) {

        if(rating == this.rating) return;

        changed = true;
        changedRating = true;

        this.rating = rating;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {

        if(image == this.image) return;

        changed = true;
        changedImage = true;

        this.image = image;
    }

    public void create()
    {

    }

    public void update()
    {
        if(!changed) return;

        Log.d("PRODUCTCHANGE", "Something was changed!");

        if(changedName){Log.d("PRODUCTCHANGED", "Name was changed to " + this.name);}
        if(changedPrice){Log.d("PRODUCTCHANGED", "Price was changed to " + this.price);}
        if(changedRating){Log.d("PRODUCTCHANGED", "Rating was changed to " + this.rating);}
        if(changedImage){Log.d("PRODUCTCHANGED", "Image was changed to " + this.image);}

        clearChanges();
    }

    public void delete()
    {
        Log.d("PRODUCTDELETED", this.name + " was deleted");
    }

    public void clearChanges()
    {
        changed = false;
        changedName = false;
        changedPrice = false;
        changedRating = false;
        changedImage = false;
    }
}
