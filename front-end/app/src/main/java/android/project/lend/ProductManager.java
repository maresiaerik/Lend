package android.project.lend;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class ProductManager
{
    private ArrayList<ProductData> productList;

    private void getProductData()
    {
        productList = new ArrayList<>();

        for(int i = 0; i < 10; i++)
        {
            ProductData productData = new ProductData();

            productData.id = (i + 1);
            productData.name = "Product " + (i + 1);

            Random r = new Random();
            productData.price = r.nextFloat() * (100 - 5) + 5;
            productData.rating = r.nextInt((5-1) + 1 ) + 1;
            productData.image = "";

            productList.add(productData);
        }
    }

    public ArrayList<ProductDataItem> getDataList()
    {
        getProductData();

        ArrayList<ProductDataItem> productDataItemList = new ArrayList<>();

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

    private class ProductData
    {
        int id;
        String name;
        float price;
        int rating;
        String image;
    }
}
