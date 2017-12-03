package segproject.tmmpl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Shopping extends AppCompatActivity {

    //private static final String SHARED_PREFS_NAME = "MY_SHARED_PREF";
    EditText editTextItem;
    Button addButton;
    ListView ShoppingList;
    DatabaseReference databaseShoppingList;
    //ArrayList<String> al;
    //List<String> sop;
    //ArrayAdapter<String> aa;
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

//                al.add(0, item);
                //notify to adapter
//                aa.notifyDataSetChanged();
//                //clear edit text
//                .setText("");
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
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Item item = postSnapshot.getValue(Item.class);
                    items.add(item);
                }

                ShoppingListAdapter itemAdapter = new ShoppingListAdapter(Shopping.this,items);
                ShoppingList.setAdapter(itemAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addItem(String item) {
        String newItem = item;


        if (!TextUtils.isEmpty(newItem)) {
            String id = databaseShoppingList.push().getKey();
            Item itemAdded = new Item(id, newItem);

            databaseShoppingList.child(id).setValue(itemAdded);

            Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();
            editTextItem.setText("");
        } else {

            Toast.makeText(this, "Please enter an item", Toast.LENGTH_LONG).show();
        }

    }

    private boolean deleteItem(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("shoppingList").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Item Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


//        protected void onStart() {
//
//        super.onStart();
//
//        databaseList.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                al.clear();
//                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                    User user = postSnapshot.getValue(User.class);
//                    al.add(et);
//                }
//
//                ShopList shopAdapter = new ShopList(Shopping.this,al);
//                lv.setAdapter(shopAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }
}
//    private void updateShoppingList(String id, String username) {
//
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("list").child(id);
//
//        User user = new User(id, username);
//
//        dR.setValue(user);
//
//        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
//    }
//
//    private boolean deleteShoppingList(String id) {
//
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("list").child(id);
//        dR.removeValue();
//        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();
//        return true;
//    }

//}


    /*private static final String SHARED_PREFS_NAME = "MY_SHARED_PREF";
    ArrayList<String> mylist = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        ListView listview = (ListView) findViewById(R.id.list);
        Button btn = (Button) findViewById(R.id.button);

        mylist = getArray();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mylist);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int arg2,long arg3) {

                String item = mylist.get(arg2);
                Toast.makeText(getApplicationContext(), item, 0).show();
            }
        });

        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {

//                String item = et.getText().toString();
//                al.add(0, item);
//
//                aa.notifyDataSetChanged();
//
//                et.setText("");
                EditText edit = (EditText) findViewById(R.id.edittext);
                String item = edit.getText().toString();
                mylist.add(0,item);
                adapter.notifyDataSetChanged();
                edit.setText("");

            }
        };
        btn.setOnClickListener(listener);
        adapter.notifyDataSetChanged();
    }

    public boolean saveArray() {
        SharedPreferences sp = this.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor mEdit1 = sp.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(mylist);
        mEdit1.putStringSet("list", set);
        return mEdit1.commit();
    }

    public ArrayList<String> getArray() {
        SharedPreferences sp = this.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);

        //NOTE: if shared preference is null, the method return empty Hashset and not null
        Set<String> set = sp.getStringSet("list", new HashSet<String>());

        return new ArrayList<String>(set);
    }

    public void onStop() {
        saveArray();
        super.onStop();
    }
}*/
    //step2 : create all the variables.





//        al = new ArrayList<String>();//initialize array list
//        //al = new List<String>();
//        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);//step4 : establish communication bw arraylist and adapter

//        lv.setAdapter(aa);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View v, int arg2, long arg3) {
//
//                String item = al.get(arg2);
//
//                Toast.makeText(getApplicationContext(), "item added!", Toast.LENGTH_LONG).show();
//            }
//        });





//
//
//    EditText et;
//    Button b;
//    ListView lv;
//    ArrayList<String> al;
//    ArrayAdapter<String> aa;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shopping);
//        //intitalize all the variables.
//
//
//        et = (EditText) findViewById(R.id.editText1);
//        b = (Button) findViewById(R.id.button1);
//        lv = (ListView) findViewById(R.id.listView1);
//
//
//        al = new ArrayList<String>();//initialize array list
//
//        aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);//step4 : establish communication bw arraylist and adapter
//        //step5 : establish communication bw adapter and dest (listview)
//
//        lv.setAdapter(aa);
//
//        lv.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View v, int arg2, long arg3) {
//
//                String item = al.get(arg2);
//
//                Toast.makeText(getApplicationContext(), item, 0).show();
//            }
//        });
//        //step6 : button click logic
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //step i: take text from et and add to arraylist
//
//                String item = et.getText().toString();
//                al.add(0, item);
//
//                //step ii: notify to adapter
//
//                aa.notifyDataSetChanged();
//
//                //step iii: clr edit text
//
//                et.setText("");
//            }
//        });
//    }
//}


//    /** Items entered by the user is stored in this ArrayList variable */
//    ArrayList<String> list = new ArrayList<String>();
//
//
//
//    /** Declaring an ArrayAdapter to set items to ListView */
//    ArrayAdapter<String> adapter;
//
//    EditText editText;
//
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        /** Setting a custom layout for the list activity */
//        setContentView(R.layout.activity_shopping);
//
//        /** Reference to the button of the layout main.xml */
//        Button btn = (Button) findViewById(R.id.btnAdd);
//
//        editText = (EditText) findViewById(R.id.txtItem);
//
//        /** Defining the ArrayAdapter to set items to ListView */
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
//
//
//
//        /** Defining a click event listener for the button "Add" */
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.add(editText.getText().toString());
//                editText.setText("");
//                adapter.notifyDataSetChanged();
//            }
//        };
//
//        /** Setting the event listener for the add button */
//        btn.setOnClickListener(listener);
//
//        /** Setting the adapter to the ListView */
//        adapter.notifyDataSetChanged();
//    }
//
//}

////CODE COMPLIES... BUT DOES NOT ADD ITEM TO THE LIST WHEN BUTTONG IS CLICKED
//
//    ListView lv;
//    ArrayAdapter<String> adapter;
//    ArrayList<String> listItems;
//    EditText itemText;
//    //TextView textView;
//    Button btnAdd;
//    CheckedTextView clicked_check_view;
//
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shopping);
//
//
//
//        clicked_check_view= (CheckedTextView) findViewById(R.id.checkedTextView1);
//        lv = (ListView) findViewById(R.id.list);
//
//        itemText = (EditText) findViewById(R.id.addText);
//
//        btnAdd = (Button) findViewById(R.id.addButton);
//
//        listItems = new ArrayList<>();
//
//        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//        adapter = new ArrayAdapter<String>(Shopping.this, android.R.layout.simple_list_item_1, listItems);
//
//
//        View.OnClickListener addlistner = new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                listItems.add(itemText.getText().toString());
//                itemText.setText("");
//                adapter.notifyDataSetChanged();
//            }
//        };
//
//        btnAdd.setOnClickListener(addlistner);
//    }
//
//        clicked_check_view.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (clicked_check_view.isChecked())
//                clicked_check_view.setChecked(false);
//            else
//                clicked_check_view.setChecked(true);
//
//        }
//    });
//
//
//}












//the question for stack overflow, some parts that they implemented
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shopping);
//
//        btnAdd = (Button) findViewById(R.id.addButton);
//        btnAdd.setOnClickListener();
//        itemText = (EditText) findViewById(R.id.editText);
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
//
//        // set the lv variable to your list in the xml
//        lv = (ListView) findViewById(R.id.list);
//        lv.setAdapter(adapter);
//    }
//
//    public void onClick(View v) {
//        String input = itemText.getText().toString();
//        if (input.length() > 0) {
//            // add string to the adapter, not the listview
//            adapter.add(input);
//            // no need to call adapter.notifyDataSetChanged(); as it is done by the adapter.add() method
//        }
//    }
//}


//the delete method, but i dont think i need it
//lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public boolean onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SparseBooleanArray positionchecker = lv.getCheckedItemPositions();
//                int count = lv.getCount();
//                for(int item = count -1;item>=0;item--){
//                    if(positionchecker.get(item)){
//                        adapter.remove(itemList.get(item));
//
//                        Toast.makeText(Shopping.this, "Item deleted",Toast.LENGTH_SHORT);
//                    }
//                }
//
//                positionchecker.clear();
//                adapter.notifyDataSetChanged();
//
//                return false;

//the first way to implement
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shopping);
//
//        btnAdd = (Button) findViewById(R.id.addbtn);
//        itemText = (EditText) findViewById(R.id.editText);
//        lv = (ListView) findViewById(R.id.list);
//
//
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
//        lv.setAdapter(adapter);
//        // set the lv variable to your list in the xml
//
//        View.OnClickListener addlistener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.add(itemText.getText().toString());
//                itemText.setText("");
//                adapter.notifyDataSetChanged();
//            }
//        };
//        btnAdd.setOnClickListener(addlistener);
//
//    }
//}

//public class Shopping extends AppCompatActivity {
//
////CODE COMPLIES... BUT DOES NOT ADD ITEM TO THE LIST WHEN BUTTONG IS CLICKED
//
//    ListView lv;
//    ArrayAdapter<String> adapter;
//    ArrayList<String> listItems;
//    EditText itemText;
//    //TextView textView;
//    Button btnAdd;
//
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shopping);
//
//
//        lv = (ListView) findViewById(R.id.list);
//
//        itemText = (EditText) findViewById(R.id.addText);
//
//        btnAdd = (Button) findViewById(R.id.addButton);
//
//        //tv=(TextView)findViewById(R.id.textView1)
//
//        listItems = new ArrayList<>();
//
//        adapter = new ArrayAdapter<String>(Shopping.this, android.R.layout.simple_list_item_1, listItems);
//
//        lv.setAdapter(adapter);
//
//        final String input =  itemText.getText().toString();
//
//
//        View.OnClickListener addlistner = new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                listItems.add(itemText.getText().toString());
//                itemText.setText("");
//                adapter.notifyDataSetChanged();
//            }
//        };
//
//        btnAdd.setOnClickListener(addlistner);
//
//    }
//
//
//}
