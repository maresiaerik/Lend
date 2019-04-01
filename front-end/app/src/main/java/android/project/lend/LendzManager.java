package android.project.lend;

import java.util.ArrayList;

public class LendzManager extends Helper {

    private ArrayList<LendzData> lendzList = new ArrayList<>();
    private ArrayList<UserData> userList = new ArrayList<>();

    ProductManager productManager = new ProductManager();
    UserManager userManager = new UserManager();

    public LendzManager(){

        lendzList = getLendzData();
        userList = getUserData();
    }

    public ArrayList<LendzDataItem> getLendzList() {

        ArrayList<LendzDataItem> lendzDataItemList = new ArrayList<>();

        for (final LendzData lendz : lendzList) {

            LendzDataItem lendzDataItem = setLendz(lendz);

            lendzDataItemList.add(lendzDataItem);
        }

        return lendzDataItemList;
    }

    public LendzDataItem getLendz(Integer id) {

        for(final LendzData lendz : lendzList){

            if(lendz.id == id)
                return setLendz(lendz);
        }

        return null;
    }

    private LendzDataItem setLendz(LendzData lendz) {

        LendzDataItem lendzDataItem = new LendzDataItem();

        lendzDataItem.setId(lendz.id);
        lendzDataItem.product = productManager.getProduct(lendz.productId);
        lendzDataItem.lender = userManager.getUser(lendz.lenderUserId);
        lendzDataItem.setStartDate(lendz.startDate);
        lendzDataItem.setDueDate(lendz.dueDate);

        lendzDataItem.clearChanges();

        return lendzDataItem;
    }
}
