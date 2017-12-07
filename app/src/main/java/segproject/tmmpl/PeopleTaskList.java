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

/**
 * Activity for people page
 */
public class PeopleTaskList extends AppCompatActivity {

    DatabaseReference databaseUsers;
    DatabaseReference databaseNextTask;
    ArrayList<User> users = new ArrayList<>();

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
    }
}
