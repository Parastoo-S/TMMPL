package com.example.mahdi.takspage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
ListView lst;
    String[] fruitname = {"Clean Car","Take Out Trash","Clean Room","Pickup Allie","Cancel Dentist Appointment","Go Shopping"};
    String[] desc={"Deadline: 11/15/2017","Deadline: 11/12/2017","Note: Vaccume afterwards too","Deadline: 11/11/2017 at 3:15pm", "Deadline: 11/10/2017","Note: Do it ASAP"};
    Integer[] imgid={R.drawable.i1,R.drawable.i2,R.drawable.i6,R.drawable.plus,R.drawable.i4,R.drawable.i3};
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst=(ListView)findViewById(R.id.listview);
        CustomListview customListview = new CustomListview(this,fruitname,desc,imgid,checkBox);
        lst.setAdapter(customListview);
    }
}
