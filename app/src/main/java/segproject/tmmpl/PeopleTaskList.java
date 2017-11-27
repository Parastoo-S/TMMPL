package segproject.tmmpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PeopleTaskList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private TextView mUserName;
    public ArrayList<User> userNameList = new ArrayList<>();
    ListView listViewUsers;
    // public var databaseReference = firebase.database();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_task_list);

       /* ValueEventListener userListener = new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                //get user name and use to update UI
                User user = dataSnapshot.getValue(User.class);

                userNameList.add(user.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };  */

        /*ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                User user = dataSnapshot.getValue(User.class);
                userNameList.add(user.getUsername());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };  */

       /* mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren() ){
                    userNameList.add(userSnapshot.getValue(User.class).getUsername());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); */

        /*userNameList.add("Will");
        userNameList.add("Rex");
        userNameList.add("Rugamba");
        userNameList.add("Jack");   */

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        String[] choreList = {"Alex", "Rex", "Alise", "Jack"};
        int[] avatarList = {R.drawable.i1, R.drawable.i2, R.drawable.i6, R.drawable.i3, R.drawable.i3, R.drawable.i3};
        String[] completedList = {"0", "3", "1", "10", "7", "69"};
        String[] nextTaskList = {"Clean up kitchen", "Take out garbage", "----", "Buy new TV","asfsdf","aefsdfsdf"};
        //ListView listView = (ListView) findViewById(R.id.peopleList);

        listViewUsers = (ListView) findViewById(R.id.peopleList);

        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = userNameList.get(i);
                //showUpdateDeleteDialog(user.getId(), user.getUsername());
                return true;
            }
        });

        //PeopleCustomAdapter adapter = new PeopleCustomAdapter(this, userNameList, avatarList, completedList, nextTaskList);
        //listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {

        super.onStart();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userNameList.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    userNameList.add(user);
                }

                String[] choreList = {"Alex", "Rex", "Alise", "Jack"};
                int[] avatarList = {R.drawable.i1, R.drawable.i2, R.drawable.i6, R.drawable.i3};
                String[] completedList = {"0", "3", "1", "10"};
                String[] nextTaskList = {"Clean up kitchen", "Take out garbage", "----", "Buy new TV",};

                PeopleCustomAdapter adapter = new PeopleCustomAdapter(PeopleTaskList.this, userNameList, avatarList, completedList, nextTaskList);
                listViewUsers.setAdapter(adapter);
                //UserList usersAdapter = new UserList(PeopleTaskList.this,userNameList);
                //listViewUsers.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
