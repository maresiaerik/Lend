package android.project.lend;

import android.util.Log;

import java.util.ArrayList;

public class ImageManager extends Helper {

    public ArrayList<ImageData> imageList;

    public ImageManager(){

        imageList = getImageData();
    }

    public ArrayList<ImageDataItem> getImageList(){

        ArrayList<ImageDataItem> imageDataItems = new ArrayList<>();

        for (final ImageData image : imageList) {

            ImageDataItem imageDataItem = setImage(image);

            imageDataItems.add(imageDataItem);
        }

        return imageDataItems;
    }

    public ImageDataItem getImage(Integer id){

        for(final ImageData image : imageList){

            if(image.id == id)
                return setImage(image);
        }

        return null;
    }

    public ArrayList<ImageDataItem> getProductImages(Integer productId)
    {
        ArrayList<ImageDataItem> imageDataItems = new ArrayList<>();

        for(final ImageData image : imageList){

            if(image.productId == productId) {

                ImageDataItem imageDataItem = setImage(image);

                imageDataItems.add(imageDataItem);
            }
        }

        return imageDataItems;
    }

    public ImageDataItem setImage(ImageData image){

        ImageDataItem imageDataItem = new ImageDataItem();

        imageDataItem.setId(image.id);
        imageDataItem.setProductId(image.productId);
        imageDataItem.setUrl(image.url);

        imageDataItem.clearChanges();

        return imageDataItem;
    }
}
