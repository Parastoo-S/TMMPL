package segproject.tmmpl;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
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

import java.util.Arrays;

import java.util.Calendar;
import java.util.List;

public class AddTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText editTextTaskName;
    EditText editTextDescription;
    EditText editTextEquipment;
    Button saveTaskButton;
    DatabaseReference databaseTasks;
    DatabaseReference databaseUsers;
    Button pickDateTime;
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    Button assignUser;
    TextView datesPicked;
    TextView formatted;
    long dueDate;
    ArrayList<User> users;
    User currentUser;
    User assignedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
//        Intent intent = getIntent();
//        currentUser = (User) intent.getSerializableExtra("activeUser");
        currentUser = User.getActiveUser();
        editTextTaskName = (EditText) findViewById(R.id.editTextTaskName);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);

        editTextEquipment = (EditText) findViewById(R.id.editTextEquipment);

        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        saveTaskButton = (Button) findViewById(R.id.saveTaskButton);
        pickDateTime = (Button) findViewById(R.id.pickDateTime);

        assignUser = (Button) findViewById(R.id.assignUser);

        formatted = (TextView) findViewById(R.id.formatted);

        saveTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
                finish();
            }
        });

        assignUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAssignUserDialog();
            }
        });


        pickDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTask.this, AddTask.this, year, month, day);
                datePickerDialog.show();

            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2){
        yearFinal = i;
        monthFinal = i1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTask.this, AddTask.this, hour, minute, DateFormat.is24HourFormat(this));

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
    private void addTask() {
        String name = editTextTaskName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

       // User activeUser = Singleton.getInstance();

        List<String> equipment = Arrays.asList(editTextEquipment.getText().toString().split(","));


        if(!TextUtils.isEmpty(name)){
            String id = databaseTasks.push().getKey();
            Task task = new Task(id, name, description, currentUser, assignedUser, dueDate,equipment);
//            currentUser.addCreatedTask(task);
//            task.setCreator(currentUser);

            databaseTasks.child(id).setValue(task);

//            currentUser.addAssignedTask(task.getTaskId());

//            databaseUsers.child(currentUser.getId()).child("assignedTaskIds").push().setValue(currentUser.getAssignedTasks());

           // User.getActiveUser().addAssignedTask(task);

//            for(String add : equipment){
//                databaseTasks.child(id).child("equipment").push().setValue(add);
//            }
           // for(User current : databaseTasks.child("users")){

           // }
            editTextTaskName.setText("");
            editTextDescription.setText("");

           // task.addCreatorUser(User.getActiveUser());




            editTextEquipment.setText("");

            Toast.makeText(this,"task added", Toast.LENGTH_LONG).show();
//            Toast.makeText(this,task.getCreatorUser().getUsername(), Toast.LENGTH_LONG).show();
//            Toast.makeText(this,task.getTaskName(), Toast.LENGTH_LONG).show();
        } else{

            Toast.makeText(this,"Please enter a name", Toast.LENGTH_LONG).show();
        }

    }

    private void showAssignUserDialog(){
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


       UserList usersAdapter = new UserList(AddTask.this,users);

        possibleAssignees.setAdapter(usersAdapter);
        dialogBuilder.setView(dialogView);


        final AlertDialog b = dialogBuilder.create();
        b.show();

        possibleAssignees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = users.get(i);
                assignedUser = user;
                b.dismiss();
            }
        });

    }


}
