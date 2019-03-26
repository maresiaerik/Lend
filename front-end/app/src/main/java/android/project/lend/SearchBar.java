package android.project.lend;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class SearchBar {


    ArrayList<ProductDataItem> productDataItemList = new ArrayList<>();
    ProductManager list;
    ArrayList<ProductDataItem> searchItems = new ArrayList<>();

    public SearchBar(ProductManager list) {
        this.list = list;
    }

    public ArrayList<ProductDataItem> search(String word) {

        productDataItemList = this.list.getDataList();

        for (int i = 0; i < productDataItemList.size(); i++) {
            if(productDataItemList.get(i).getName().contains(word)) {
                searchItems.add(productDataItemList.get(i));
            }
        }

        return searchItems;
    }


}
