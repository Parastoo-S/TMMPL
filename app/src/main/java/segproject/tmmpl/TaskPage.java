package segproject.tmmpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lst;
    String[] taskName = {"Clean Car", "Take Out Trash", "Clean Room", "Pickup Allie", "Cancel Dentist Appointment", "Go Shopping"};
    String[] taskDescription = {"Deadline: 11/15/2017", "Deadline: 11/12/2017", "Note: Vaccume afterwards too", "Deadline: 11/11/2017 at 3:15pm", "Deadline: 11/10/2017", "Note: Do it ASAP"};
    Integer[] userProfileImage = {R.drawable.i1, R.drawable.i2, R.drawable.i6, R.drawable.plus, R.drawable.i4, R.drawable.i3};
    CheckBox isComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst = (ListView) findViewById(R.id.addedTaskListView);
        CustomListview customListview = new CustomListview(this, taskName, taskDescription, userProfileImage, isComplete);
        lst.setAdapter(customListview);

        FloatingActionButton addTaskFab = (FloatingActionButton) findViewById(R.id.addTaskFab);
        addTaskFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), AddTask.class);
                startActivityForResult(myIntent, 0);
            }

        });


    }
}

