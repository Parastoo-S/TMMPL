package segproject.tmmpl;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Calendar;
import java.util.List;

/**
 * This activity for viewing a task
 */
public class ViewTask extends AppCompatActivity {

    private Task activeTask;
    TextView assignedUserName;
    EditText description;
    TextView dueDate;
    TextView dueTime;
    TextView creatorName;
    TextView taskTitle;
    ImageView editButton;
    ImageView deleteButton;
    TextView equipmentList;

    /**
     * Populates the page with data from database
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_view_task);

        activeTask = Task.getActiveTask();
        taskTitle = (TextView) findViewById(R.id.taskTitle);
        assignedUserName = (TextView) findViewById(R.id.assignedUserName);
        description = (EditText) findViewById(R.id.description);
        description.setEnabled(false);
        dueDate = (TextView) findViewById(R.id.dueDate);
        dueTime = (TextView) findViewById(R.id.dueTime);
        creatorName = (TextView) findViewById(R.id.creatorName);
        equipmentList = (TextView)findViewById(R.id.equipmentList);

        editButton = (ImageView) findViewById(R.id.editTask);
        deleteButton = (ImageView) findViewById(R.id.deleteTask);

        taskTitle.setText(activeTask.getTaskName());

        creatorName.setText(activeTask.getCreatorUser().getUsername());
        if (activeTask.getAssignedUser() != null){
            assignedUserName.setText(activeTask.getAssignedUser().getUsername());
        }
        else{
            assignedUserName.setText("No Users assigned");
        }

        description.setText(activeTask.getDescription());

        String equipments="";
        for (String s : activeTask.getEquipments())
        {
            equipments +="•"+ s + "\n";
        }
        equipmentList.setText(equipments);



        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(activeTask.getDueDate());

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);


        dueDate.setText(mYear + "/" + mMonth + "/" + mDay);
        dueTime.setText(mHour + ":" + mMinute);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTask.this, EditTask.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ViewTask.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure you want to delete this task?");
                alert.setPositiveButton("YES, DELETE", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTask(activeTask.getTaskId());
                        dialog.dismiss();
                        finish();

                    }
                });
                alert.setNegativeButton("NO, KEEP", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();


            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();

        activeTask = Task.getActiveTask();
        taskTitle.setText(activeTask.getTaskName());
        description.setText(activeTask.getDescription());


        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(activeTask.getDueDate());

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);


        dueDate.setText(mYear + "/" + mMonth + "/" + mDay);
        dueTime.setText(mHour + ":" + mMinute);
        if (activeTask.getAssignedUser() != null){
            assignedUserName.setText(activeTask.getAssignedUser().getUsername());
        }
        else{
            assignedUserName.setText("No Users assigned");
        }
    }


    /**
     * This method deletes a task from database
     * @param id
     * @return
     */

    private boolean deleteTask(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("tasks").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

}
