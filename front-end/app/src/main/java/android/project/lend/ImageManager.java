package android.project.lend;

import java.util.ArrayList;

public class ImageManager extends Helper implements IManager {

    private IDataController dataController;
    public Boolean loaded = false;

    public ArrayList<ImageData> imageList = new ArrayList<>();

    private IManager parentManager;

    public ImageManager(IDataController dataController, IManager parentManager){

        this.dataController = dataController;
        this.parentManager = parentManager;

        getImageData(this, imageList);
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

        //new ImageDownloader(imageDataItem.imageView).execute(image.url);

        imageDataItem.clearChanges();

        return imageDataItem;
    }

    @Override
    public void setLoaded(Boolean loaded) {

        this.loaded = loaded;

        if(parentManager != null)
            parentManager.checkStatus();
        else
            checkStatus();
    }

    @Override
    public void checkStatus() {

        if(!loaded) return;

        dataController.setData();
    }
}
