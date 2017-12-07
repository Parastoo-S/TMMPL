package segproject.tmmpl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for main Task page
 */
public class TaskPage extends AppCompatActivity {


    ListView listViewTasks;
    DatabaseReference databaseTasks;
    List<Task> tasks;
    User currentUser;
    Switch buttonShowUsersTasks;
    DatabaseReference databaseUserTasks;
    ArrayList<String> taskIds = new ArrayList<>();
    ArrayList<Task> activeTasks = new ArrayList<>();
    FloatingActionButton addTaskFab;
    private DrawerLayout nDrawerLayout;
    private ActionBarDrawerToggle nToggle;

    /**
     *Activity for task page
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);

        nDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open ,R.string.close );

        nDrawerLayout.addDrawerListener(nToggle);
        nToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewTasks = (ListView) findViewById(R.id.listViewTasks);
        currentUser = User.getActiveUser();

        buttonShowUsersTasks = (Switch) findViewById(R.id.showSwitch);


        addTaskFab = (FloatingActionButton) findViewById(R.id.addTaskFab);


        addTaskFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent newActivity = new Intent(TaskPage.this, AddTask.class);
                startActivity(newActivity);
            }
        });

        databaseUserTasks = FirebaseDatabase.getInstance().getReference("users").child(User.getActiveUser().getId()).child("assignedTaskIds");


        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        tasks = new ArrayList<>();


        buttonShowUsersTasks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton view, boolean checked){
                if(checked){
                    activeUserTasks();
                }else{
                    TaskList tasksAdapter = new TaskList(TaskPage.this,tasks);
                    listViewTasks.setAdapter(tasksAdapter);
                }
            }
        });

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Task task = tasks.get(i);

                task.setActiveTask(task);

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
                if(check.getTaskId().equals(id)){
                    activeTasks.add(check);
                }
            }
        }

        TaskList activeTasksAdapter = new TaskList(TaskPage.this,activeTasks);
        listViewTasks.setAdapter(activeTasksAdapter);

    }

    /**
     * side bar menu navigation (On click does not work, an overflow menu was substituted
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(nToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


// OnClick methods for overflow menu
    public void goToTasks(MenuItem menuItem){
        Intent newActivity = new Intent(TaskPage.this, TaskPage.class);
        startActivity(newActivity);
    }
    public void goToPeople(MenuItem menuItem){
        Intent newActivity = new Intent(TaskPage.this, PeopleTaskList.class);
        startActivity(newActivity);
    }

    public void goToShopping(MenuItem menuItem){
        Intent newActivity = new Intent(TaskPage.this, Shopping.class);
        startActivity(newActivity);
    }

    public void goToLogin(MenuItem menuItem){
        Intent newActivity = new Intent(TaskPage.this, UserLogin.class);
        startActivity(newActivity);
    }


    /**
     * inflated for side bar menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new_quick_access, menu);
        return true;
    }


}


