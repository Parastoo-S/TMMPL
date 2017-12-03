package segproject.tmmpl;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Parastoo on 12/2/2017.
 */

public class ShoppingListAdapter extends ArrayAdapter<Item> {

    private Activity context;
    List<Item> items;


    public ShoppingListAdapter(Activity context, List<Item> items) {
        super(context, R.layout.layout_shopping_list_adapter, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_shopping_list_adapter, null, true);

        TextView itemName = (TextView) listViewItem.findViewById(R.id.itemName);
//        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);

        Item item = items.get(position);
        itemName.setText(item.getItemName());
        return listViewItem;
    }
}
