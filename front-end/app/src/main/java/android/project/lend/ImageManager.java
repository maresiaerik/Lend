package android.project.lend;

import java.util.ArrayList;

public class ImageManager extends Helper {

    public ArrayList<ImageData> imageList;

    public ArrayList<ImageDataItem> getDataList(Integer id){

        ArrayList<ImageDataItem> image_data_item_list = new ArrayList<>();

        imageList = getImageData(id);

        for (ImageData image : imageList) {

            ImageDataItem image_data_item = new ImageDataItem();

            image_data_item.setId(image.id);
            image_data_item.setProductId(image.product_id);
            image_data_item.setUrl(image.url);

            image_data_item.clearChanges();

            image_data_item_list.add(image_data_item);
        }

        return image_data_item_list;
    }
}
