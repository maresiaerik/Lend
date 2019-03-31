package android.project.lend;

import java.util.ArrayList;

public class ProductManager extends Helper
{
    public ArrayList<ProductData> productList;
    public ArrayList<ImageData> imageList;

    public ProductManager(){

        productList = getProductData();
        imageList = getImageData();
    }

    public ArrayList<ProductDataItem> getProductList()
    {
        ArrayList<ProductDataItem> productDataItemList = new ArrayList<>();

        for (final ProductData product : productList)
        {
            ProductDataItem productDataItem = setProduct(product);

            productDataItemList.add(productDataItem);
        }

        return productDataItemList;
    }

    public ProductDataItem getProduct(Integer id){

        for (final ProductData product : productList) {

            if(product.id == id)
                return setProduct(product);
        }

        return null;
    }

    private ProductDataItem setProduct(ProductData product) {

        ProductDataItem productDataItem = new ProductDataItem();

        productDataItem.setId(product.id);
        productDataItem.setName(product.name);
        productDataItem.setPrice(product.price);
        productDataItem.setRating(product.rating);
        productDataItem.imageDataItems = filterImages(product.id);

        productDataItem.clearChanges();

        return productDataItem;
    }

    private ArrayList<ImageDataItem> filterImages(Integer productId)
    {
        ArrayList<ImageDataItem> imageDataItems = new ArrayList<>();

        for(final ImageData image : imageList){

            if(image.productId == productId) {

                ImageDataItem imageDataItem = new ImageDataItem();

                imageDataItem.setId(image.id);
                imageDataItem.setProductId(productId);
                imageDataItem.setUrl(image.url);

                imageDataItem.clearChanges();

                imageDataItems.add(imageDataItem);
            }
        }

        return imageDataItems;
    }
}
