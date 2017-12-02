package segproject.tmmpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskPage extends AppCompatActivity {
//    ListView lst;
//    String[] taskName = {"Clean Car", "Take Out Trash", "Clean Room", "Pickup Allie", "Cancel Dentist Appointment", "Go Shopping"};
//    String[] taskDescription = {"Deadline: 11/15/2017", "Deadline: 11/12/2017", "Note: Vaccume afterwards too", "Deadline: 11/11/2017 at 3:15pm", "Deadline: 11/10/2017", "Note: Do it ASAP"};
//    Integer[] userProfileImage = {R.drawable.i1, R.drawable.i2, R.drawable.i6, R.drawable.plus, R.drawable.i4, R.drawable.i3};
//    CheckBox isComplete;


    EditText editTextTaskName;
    EditText editTextDescription;
    ListView listViewTasks;
    DatabaseReference databaseTasks;
    List<Task> tasks;
    CheckBox completed;
    User currentUser;
    Switch buttonShowUsersTasks;
    DatabaseReference databaseUserTasks;
    DatabaseReference databaseEquipmentsTask;
    ArrayList<String> taskIds = new ArrayList<>();
    ArrayList<Task> activeTasks = new ArrayList<>();
    FloatingActionButton addTaskFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);


//        FloatingActionButton addTaskFab = (FloatingActionButton) findViewById(R.id.addTaskFab);
//        addTaskFab.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                Intent myIntent = new Intent(view.getContext(), AddTask.class);
//                startActivityForResult(myIntent, 0);
//            }
//
//        });

//        editTextTaskName = (EditText) findViewById(R.id.editTextTaskName);
//        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        listViewTasks = (ListView) findViewById(R.id.listViewTasks);
        //addTaskButton = (Button) findViewById(R.id.addTaskButton);
        currentUser = User.getActiveUser();

        buttonShowUsersTasks = (Switch) findViewById(R.id.showSwitch);

        //buttonShowUsersTasks = (Button) findViewById(R.id.showSwitch);
        addTaskFab = (FloatingActionButton) findViewById(R.id.addTaskFab);


        addTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskPage.this, AddTask.class));
            }
        });



        databaseUserTasks = FirebaseDatabase.getInstance().getReference("users").child(User.getActiveUser().getId()).child("assignedTaskIds");

        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        tasks = new ArrayList<>();

        // TODO: Check this out
        //buttonShowUsersTasks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

        buttonShowUsersTasks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton view, boolean checked){
                if(!checked){
                    TaskList tasksAdapter = new TaskList(TaskPage.this,tasks);
                    listViewTasks.setAdapter(tasksAdapter);
                }else{

                    activeUserTasks();
                }
            }
        });


//        TextView activeUser = (TextView) findViewById(R.id.activeUser);
//        activeUser.setText(currentUser.getUsername());


        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = tasks.get(i);

                task.setActiveTask(task);

//                Toast.makeText(getApplicationContext(), currentUser.getUsername(), Toast.LENGTH_LONG).show();
                Intent newActivity = new Intent(TaskPage.this, ViewTask.class);
                startActivity(newActivity);

            }
        });


    }
    protected void onStart() {

        super.onStart();

        databaseTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasks.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Task task = postSnapshot.getValue(Task.class);
                    tasks.add(task);
                }

                TaskList tasksAdapter = new TaskList(TaskPage.this,tasks);
                listViewTasks.setAdapter(tasksAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void activeUserTasks(){
        databaseUserTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskIds.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    taskIds.add(postSnapshot.getValue().toString());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        activeTasks.clear();
        for(Task check : tasks){
            for(String id : taskIds){
                //TODO: it is not grabbing the proper info form the database
                if(check.getTaskId().equals(id)){
                    activeTasks.add(check);
                }
            }
        }

        TaskList activeTasksAdapter = new TaskList(TaskPage.this,activeTasks);
        listViewTasks.setAdapter(activeTasksAdapter);

//        databaseTasks.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //tasks.clear();
//                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                    Task task = postSnapshot.getValue(Task.class);
//                    for(String id : taskIds){
//                        if(task.getTaskId().equals(id)) {
//                            activeTasks.add(task);
//                        }
//                    }
//                }
//
//                TaskList tasksAdapter = new TaskList(TaskPage.this,activeTasks);
//                listViewTasks.setAdapter(tasksAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }



    public void startNewTaskActivity(View view){
        Intent addTask = new Intent(TaskPage.this, AddTask.class);
        startActivity(addTask);
    }

}


