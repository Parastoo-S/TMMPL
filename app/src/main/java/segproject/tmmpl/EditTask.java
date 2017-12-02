package segproject.tmmpl;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;
    long dueDate;
    TextView formatted;
    Button saveChangeTask;
    Task activeTask;
    Button deallocateUser;
    Button allocateUser;
    ArrayList<User> users;
    User assignedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
//        Intent intent = getIntent();
//        activeTask = (Task) intent.getSerializableExtra("activeTask");
        activeTask = Task.getActiveTask();

        final EditText editTaskTitle = (EditText) findViewById(R.id.editTaskTitle);
        final EditText editDescription = (EditText) findViewById(R.id.editDescription);
        final Button editDateTime = (Button) findViewById(R.id.editDateTime);
        formatted = (TextView) findViewById(R.id.formatted);
        saveChangeTask = (Button) findViewById(R.id.saveChangeTask);
        allocateUser = (Button) findViewById(R.id.allocateUser);
        deallocateUser = (Button) findViewById(R.id.deallocateUser);

        editDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTask.this, EditTask.this, year, month, day);
                datePickerDialog.show();

            }
        });

        saveChangeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = activeTask;


                String name = editTaskTitle.getText().toString().trim();
                String description = editDescription.getText().toString().trim();
                long dueDateTime = dueDate;

                if (!TextUtils.isEmpty(name)) {
                    activeTask.setTaskName(name);
                }
                if(!TextUtils.isEmpty(description)) {
                    activeTask.setDescription(description);
                }

                if (dueDate != 0) {
                    activeTask.setDueDate(dueDate);
                }



                updateTask(activeTask.getTaskId(), activeTask);
//
//                Intent intent = new Intent();
//                intent.putExtra("updatedTask", activeTask);
//
//                setResult(RESULT_OK, intent);
                finish();

            }
        });

//These 2 event listeners do not work
        deallocateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task.getActiveTask().removeAssignedUser();
            }
        });

        allocateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User allocatedUser = showAssignUserDialog();
                Task.getActiveTask().setAssignedUser(allocatedUser);


            }
        });




    }


    private User showAssignUserDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();


        final View dialogView = inflater.inflate(R.layout.assign_user_dialog, null);
        ListView possibleAssignees = (ListView) dialogView.findViewById(R.id.possibleAssignees);
        users = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        UserList usersAdapter = new UserList(EditTask.this,users);

        possibleAssignees.setAdapter(usersAdapter);
        dialogBuilder.setView(dialogView);


        final AlertDialog b = dialogBuilder.create();
        b.show();

        possibleAssignees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = users.get(i);
                assignedUser = user;
                activeTask.setAssignedUser(user);
                b.dismiss();
            }
        });

        return assignedUser;

    }
    private void updateTask(String id, Task task) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("tasks").child(id);

       dR.setValue(task);

        Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
        yearFinal = i;
        monthFinal = i1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(EditTask.this, EditTask.this, hour, minute, DateFormat.is24HourFormat(this));

        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1){
        hourFinal = i;
        minuteFinal = i1;

        Calendar c = Calendar.getInstance();
        c.set(yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal);
        dueDate = c.getTimeInMillis();
//        int dateTime = yearFinal + monthFinal + dayFinal + hourFinal + minuteFinal;
//        datesPicked.setText(String.valueOf(startDate));


        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        formatted.setText("Due Date Picked: " + mYear + "/" + mMonth + "/" + mDay + " at "+ mHour + ":" + mMinute);


    }
}
