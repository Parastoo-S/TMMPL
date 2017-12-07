package segproject.tmmpl;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Adapter for shopping page
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

        Item item = items.get(position);
        itemName.setText(item.getItemName());
        return listViewItem;
    }
}
