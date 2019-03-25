package android.project.lend;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<ProductDataItem> {

    private Context context;
    private List<ProductDataItem> productList = new ArrayList<>();

    public ProductAdapter(@NonNull Context context, @LayoutRes ArrayList<ProductDataItem> list) {
        super(context, 0 , list);
        this.context = context;
        productList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.product_list_item,parent,false);

        ProductDataItem currentProduct = productList.get(position);

        TextView id = (TextView) listItem.findViewById(R.id.product_id);
        id.setText(currentProduct.getId().toString());

        TextView name = (TextView) listItem.findViewById(R.id.product_name);
        name.setText(currentProduct.getName());

        return listItem;
    }
}
