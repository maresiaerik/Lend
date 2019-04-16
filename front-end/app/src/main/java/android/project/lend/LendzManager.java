package android.project.lend;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class LendzManager extends Helper implements IManager {

    private IDataController dataController;
    public Boolean loaded = false;
    private ArrayList<LendzData> lendzList = new ArrayList<>();
    private ProductManager productManager;
    private UserManager userManager;
    private IManager parentManager;

    public LendzManager(IDataController dataController, IManager parentManager) {

        this.dataController = dataController;
        this.parentManager = parentManager;

        productManager = new ProductManager(dataController, this);
        userManager = new UserManager(dataController, this);

        getLendzData(this, lendzList);
    }

    public LendzManager(IDataController dataController) {

        this.dataController = dataController;

        getLendzData(this, lendzList);
    }

    public ArrayList<LendzDataItem> getLendzListByProduct(Integer productId) {

        ArrayList<LendzDataItem> lendzDataItemList = new ArrayList<>();

        for (final LendzData lendz : lendzList) {

            if (lendz.productId == productId) {

                LendzDataItem lendzDataItem = setLendz(lendz);

                lendzDataItemList.add(lendzDataItem);
            }

        }

        return lendzDataItemList;
    }

    public ArrayList<LendzDataItem> getLendzListByUser(Integer userId) {

        ArrayList<LendzDataItem> lendzDataItemList = new ArrayList<>();

        for (final LendzData lendz : lendzList) {

            if (lendz.lenderUserId == userId && lendz.rating == null) {

                LendzDataItem lendzDataItem = setLendz(lendz);

                lendzDataItemList.add(lendzDataItem);
            }
        }

        sortListByDueDate(lendzDataItemList);

        return lendzDataItemList;
    }

    public ArrayList<LendzDataItem> getLendzListByRating(Integer userId) {

        ArrayList<LendzDataItem> lendzDataItemList = new ArrayList<>();

        for (final LendzData lendz : lendzList) {

            if (lendz.lenderUserId == userId && lendz.rating != null) {

                LendzDataItem lendzDataItem = setLendz(lendz);

                lendzDataItemList.add(lendzDataItem);
            }
        }

        return lendzDataItemList;
    }

    public LendzDataItem getLendzById(Integer id) {

        for (final LendzData lendz : lendzList) {

            if (lendz.id == id)
                return setLendz(lendz);
        }

        return null;
    }

    private LendzDataItem setLendz(LendzData lendz) {

        LendzDataItem lendzDataItem = new LendzDataItem();

        lendzDataItem.setId(lendz.id);

        if (productManager != null)
           lendzDataItem.product = productManager.getProduct(lendz.productId);

        if (userManager != null)
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

        if (parentManager != null)
            parentManager.checkStatus();
        else
            checkStatus();
    }

    @Override
    public void checkStatus() {

        if (!loaded) return;
        if (productManager != null && !productManager.loaded) return;
        if (userManager != null && !userManager.loaded) return;

        dataController.setData();
    }

    private void sortListByDueDate(ArrayList<LendzDataItem> list) {

        Collections.sort(list, new Comparator<LendzDataItem>() {
            @Override
            public int compare(LendzDataItem o1, LendzDataItem o2) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date d1 = null;
                Date d2 = null;

                try {
                    d1 = sdf.parse(o1.getDueDate());
                    d2 = sdf.parse(o2.getDueDate());
                } catch (ParseException e) {

                }

                return d1.compareTo(d2);
            }
        });
    }
}
