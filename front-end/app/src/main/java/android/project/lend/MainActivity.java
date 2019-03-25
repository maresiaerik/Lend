package android.project.lend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProductManager productManager = new ProductManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playground();
    }

    private void playground(){

        ArrayList<ProductDataItem> productDataItemList = new ArrayList<>();
        productDataItemList = productManager.getDataList();

        productDataItemList.get(0).setName("New name");
        productDataItemList.get(0).update();

        productDataItemList.get(5).setRating(5);
        productDataItemList.get(5).update();

        productDataItemList.get(3).delete();

        ListView listView = (ListView) findViewById(R.id.item_view);

        final ProductAdapter pa;
        pa = new ProductAdapter(this, productDataItemList);

        listView.setAdapter(pa);
    }
}
