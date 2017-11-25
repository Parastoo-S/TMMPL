package segproject.tmmpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class Shopping extends AppCompatActivity {
    private ArrayList<String> shopping_List;
    private ArrayAdapter<String> adapter;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView)findViewById(R.id.shoppingList) ;
        String[] newItems={};
        shopping_List=new ArrayList<>(Arrays.asList(newItems));
        adapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.shoppingList,shopping_List);
        listView.setAdapter(adapter);
        editText=(EditText) findViewById(R.id.newitembtn);
        Button newitembtn=(Button) findViewById(R.id.newitembtn);
        newitembtn.setOnClickListener(new View.OnClickListener(){

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newItem =editText.getText().toString();
                shopping_List.add(newItem);
                adapter.notifyDataSetChanged();
            }
        });

        }
    }







