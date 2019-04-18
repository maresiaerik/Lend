package android.project.lend;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class SearchBar {


    private ArrayList<ProductDataItem> productDataItemList;


    private ArrayList<ProductDataItem> searchItems = new ArrayList<>();



    public SearchBar(ArrayList<ProductDataItem> list ) {

        productDataItemList = list;
    }

    public ArrayList<ProductDataItem> search(CharSequence c) {

        String word = c.toString();
        if(productDataItemList != null) {
            for (int i = 0; i < productDataItemList.size(); i++) {
                if (productDataItemList.get(i).getName().toLowerCase().contains(word.toLowerCase())) {
                    searchItems.add(productDataItemList.get(i));
                }
            }
        }
        return searchItems;
    }
}