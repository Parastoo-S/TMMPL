package segproject.tmmpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for shopping page
 */
public class Shopping extends AppCompatActivity {
    //this code is closely following lab 7


    EditText editTextItem;
    Button addButton;
    ListView ShoppingList;
    DatabaseReference databaseShoppingList;
    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        //intitalize all the variables.


        editTextItem = (EditText) findViewById(R.id.editTextItem);
        addButton = (Button) findViewById(R.id.addItem);
        ShoppingList = (ListView) findViewById(R.id.ShoppingList);

        databaseShoppingList = FirebaseDatabase.getInstance().getReference("shoppingList");

        items = new ArrayList<>();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take text from et and add to arraylist
                String item = editTextItem.getText().toString();
                addItem(item);
            }
        });

        ShoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = items.get(i);
                deleteItem(item.getItemId());
            }
        });
    }

    protected void onStart() {

        super.onStart();

        databaseShoppingList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
                    items.add(item);
                }

                ShoppingListAdapter itemAdapter = new ShoppingListAdapter(Shopping.this, items);
                ShoppingList.setAdapter(itemAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addItem(String item) {

        //adds the item from shopping list to the database
        String newItem = item;

        //if user enters an item the it is pushed to the database
        //new instance of item
        //successful message is displayed
        if (!TextUtils.isEmpty(newItem)) {
            String id = databaseShoppingList.push().getKey();
            Item itemAdded = new Item(id, newItem);

            databaseShoppingList.child(id).setValue(itemAdded);

            Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();
            editTextItem.setText("");
        } else {
            //if the user doesn't enter a text
            //nothing added to firebase and message it displayed

            Toast.makeText(this, "Please enter an item", Toast.LENGTH_LONG).show();
        }

    }

    private boolean deleteItem(String id) {
        //this deletes and item from the shopping list and from database

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("shoppingList").child(id);
        dR.removeValue();
        //sets a message for successful deleting
        Toast.makeText(getApplicationContext(), "Item Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


}