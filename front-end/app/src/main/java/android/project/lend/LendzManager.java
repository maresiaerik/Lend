package android.project.lend;

import android.util.Log;

import java.util.ArrayList;

public class LendzManager extends Helper implements IManager {

    private IDataController dataController;
    public Boolean loaded = false;

    private ArrayList<LendzData> lendzList = new ArrayList<>();

    private ProductManager productManager;
    private UserManager userManager;

    private IManager parentManager;

    public LendzManager(IDataController dataController, IManager parentManager){

        this.dataController = dataController;
        this.parentManager = parentManager;

        productManager = new ProductManager(dataController, this);
        userManager = new UserManager(dataController, this);

        getLendzData(this, lendzList);
    }

    public LendzManager(IDataController dataController){

        this.dataController = dataController;

        getLendzData(this, lendzList);
    }

    public ArrayList<LendzDataItem> getLendzListByProduct(Integer productId){

        ArrayList<LendzDataItem> lendzDataItemList = new ArrayList<>();

        for (final LendzData lendz : lendzList) {

            if(lendz.productId == productId) {

                LendzDataItem lendzDataItem = setLendz(lendz);

                lendzDataItemList.add(lendzDataItem);
            }

        }

        return lendzDataItemList;
    }

    public ArrayList<LendzDataItem> getLendzListByUser(Integer userId) {

        ArrayList<LendzDataItem> lendzDataItemList = new ArrayList<>();

        for (final LendzData lendz : lendzList) {

            if(lendz.lenderUserId == userId) {

                LendzDataItem lendzDataItem = setLendz(lendz);

                lendzDataItemList.add(lendzDataItem);
            }
        }

        return lendzDataItemList;
    }

    public LendzDataItem getLendzById(Integer id) {

        for(final LendzData lendz : lendzList){

            if(lendz.id == id)
                return setLendz(lendz);
        }

        return null;
    }

    private LendzDataItem setLendz(LendzData lendz) {

        LendzDataItem lendzDataItem = new LendzDataItem();

        lendzDataItem.setId(lendz.id);

        if(productManager != null)
            lendzDataItem.product = productManager.getProduct(lendz.productId);

        if(userManager != null)
            lendzDataItem.lender = userManager.getUser(lendz.lenderUserId);

        lendzDataItem.setStartDate(lendz.startDate);
        lendzDataItem.setDueDate(lendz.dueDate);
        lendzDataItem.setRating(lendz.rating);

        lendzDataItem.clearChanges();

        return lendzDataItem;
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
        if(productManager != null && !productManager.loaded) return;
        if(userManager != null && !userManager.loaded) return;

        dataController.setData();
    }
}
