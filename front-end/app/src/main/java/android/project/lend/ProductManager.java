package android.project.lend;

import android.util.Log;

import java.util.ArrayList;

public class ProductManager extends Helper
{
    public ArrayList<ProductData> productList;

    UserManager userManager = new UserManager();
    ImageManager imageManager = new ImageManager();

    public ProductManager(){

        productList = getProductData();
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
        productDataItem.owner = userManager.getUser(product.userId);
        productDataItem.setName(product.name);
        productDataItem.setPrice(product.price);
        productDataItem.setRating(product.rating);
        productDataItem.imageDataItems = imageManager.getProductImages(product.id);
        productDataItem.setDescription(product.description);
        productDataItem.setCategory(product.category);

        productDataItem.clearChanges();

        return productDataItem;
    }
}
