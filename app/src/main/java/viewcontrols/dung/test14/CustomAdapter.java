package viewcontrols.dung.test14;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IT on 11/26/2016.
 */

public class CustomAdapter extends ArrayAdapter<Product> {

    public CustomAdapter(Context context, int item_listview, ArrayList<Product> products) {
        super(context, item_listview, products);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_listview, null);

        TextView tvId = (TextView) view.findViewById(R.id.tvId);
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);

        Product product = getItem(position);
        tvId.setText(product.id);
        tvName.setText(product.name);
        tvPrice.setText(product.price);

        return view;
    }
}
