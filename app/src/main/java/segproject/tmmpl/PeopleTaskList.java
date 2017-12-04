package segproject.tmmpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static segproject.tmmpl.R.id.list;
import static segproject.tmmpl.R.id.listViewTasks;

public class PeopleTaskList extends AppCompatActivity {

    DatabaseReference databaseTaskCount;
    DatabaseReference databaseUsers;
    DatabaseReference databaseNextTask;
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Integer> taskCount = new ArrayList<>();
    ArrayList<String> nextTask = new ArrayList<>();

    ArrayList<String> taskIds = new ArrayList<>();
    ArrayList<Task> tasks = new ArrayList<>();

    ListView listViewPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_task_list);

        listViewPeople = (ListView) findViewById(R.id.list);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseNextTask = FirebaseDatabase.getInstance().getReference("tasks");
//
        databaseNextTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tasks.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Task task = postSnapshot.getValue(Task.class);
                    tasks.add(task);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        PeopleCustomAdapter peopleAdapter = new PeopleCustomAdapter(PeopleTaskList.this,users, tasks);
        listViewPeople.setAdapter(peopleAdapter);
//
//        for(User user : users){
//            databaseTaskCount = FirebaseDatabase.getInstance().getReference("users").child(user.getId()).child("assignedTaskIds");
//
//            databaseTaskCount.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    taskIds.clear();
//                    Integer count = 0;
//                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
//                        taskIds.add(postSnapshot.getValue().toString());
//                        count++;
//                    }
//                    taskCount.add(count);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//            long minDueDate = Long.MAX_VALUE;
//            String closestTask = "";
//            for(Task task : tasks){
//                for(String id : taskIds){
//                    if(task.getTaskId().equals(id)){
//                        if(task.getDueDate() < minDueDate){
//                            closestTask = task.getTaskName();
//                            minDueDate = task.getDueDate();
//                        }
//                    }
//                }
//            }
//            nextTask.add(closestTask);
//        }

       // int[] avatarList = {R.drawable.i1, R.drawable.i2, R.drawable.i3, R.drawable.i4, R.drawable.i5, R.drawable.i6};

//        ListView listView = (ListView) findViewById(R.id.list);
//
//        PeopleCustomAdapter adapter = new PeopleCustomAdapter(this, users);

       // PeopleCustomAdapter adapter = new PeopleCustomAdapter(this, users, avatarList, taskCount, nextTask);
//        listView.setAdapter(adapter);
//        PeopleCustomAdapter peopleAdapter = new PeopleCustomAdapter(this, users, taskCount, nextTask);
//        listViewPeople.setAdapter(peopleAdapter);
    }
}
