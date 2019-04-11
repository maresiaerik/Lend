package android.project.lend;

import android.util.Log;

import java.util.ArrayList;

public class ProductManager extends Helper implements IManager
{
    private IDataController dataController;
    public Boolean loaded = false;

    public ArrayList<ProductData> productList = new ArrayList<>();

    private UserManager userManager;
    private ImageManager imageManager;

    private IManager parentManager;

    public ProductManager(IDataController dataController, IManager parentManager){

        this.dataController = dataController;
        this.parentManager = parentManager;

        userManager = new UserManager(dataController, this);
        imageManager = new ImageManager(dataController, this);

        getProductData(this, productList);
    }

    public ArrayList<ProductDataItem> getExploreProductList(UserDataItem user)
    {
        ArrayList<ProductDataItem> productDataItemList = new ArrayList<>();

        for (final ProductData product : productList)
        {
            if(user != null && product.userId != user.getId() || user == null) {

                ProductDataItem productDataItem = setProduct(product);

                productDataItemList.add(productDataItem);
            }
        }

        Log.d("PRODUCTMANAGER", productDataItemList.size() + "");
        return productDataItemList;
    }

    public ArrayList<ProductDataItem> getHomeProductList(Integer userId)
    {
        ArrayList<ProductDataItem> productDataItemList = new ArrayList<>();

        for (final ProductData product : productList)
        {
            if(product.userId == userId) {

                ProductDataItem productDataItem = setProduct(product);

                productDataItemList.add(productDataItem);
            }
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
        productDataItem.setStatus(product.status);

        productDataItem.clearChanges();

        return productDataItem;
    }

    @Override
    public void setLoaded(Boolean loaded) {

        this.loaded = loaded;

        if(parentManager != null)
            parentManager.checkStatus();
        else
            checkStatus();
    }

    public void checkStatus() {

        if(!loaded) return;
        if(!userManager.loaded) return;
        if(!imageManager.loaded) return;

        dataController.setData();
    }
}
