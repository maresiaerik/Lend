package android.project.lend;

import java.util.ArrayList;

public class ProductManager extends Helper
{
    private ArrayList<ProductData> productList;

    public ArrayList<ProductDataItem> getDataList()
    {
        ArrayList<ProductDataItem> productDataItemList = new ArrayList<>();

        productList = getProductData();

        for (ProductData product : productList)
        {
            ProductDataItem productDataItem = new ProductDataItem();

            productDataItem.setId(product.id);
            productDataItem.setName(product.name);
            productDataItem.setPrice(product.price);
            productDataItem.setRating(product.rating);
            productDataItem.setImage(product.image);

            productDataItem.clearChanges();

            productDataItemList.add(productDataItem);
        }

        return productDataItemList;
    }
}
