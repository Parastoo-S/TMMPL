package segproject.tmmpl;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.media.Image;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Activity for user login page
 */
public class UserLogin extends AppCompatActivity {
    EditText editTextUsername;

    Button buttonAddUser;
    ListView listViewUsers;
    DatabaseReference databaseUsers;
    List<User> users;
    ImageView buttonSetAvatar;

    private DrawerLayout nDrawerLayout;
    private ActionBarDrawerToggle nToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        nDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open ,R.string.close );

//        nDrawerLayout.addDrawerListener(nToggle);
//        nToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
//        editAvatartId = (EditText) findViewById(R.id.editAvatartId);

        buttonSetAvatar = (ImageView) findViewById(R.id.avatar);

        listViewUsers = (ListView) findViewById(R.id.listViewUsers);
        buttonAddUser = (Button) findViewById(R.id.newUser);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        users = new ArrayList<>();


        //onclicklistener to for new user button
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewUserDialog();
            }
        });

        listViewUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = users.get(i);
                showUpdateDeleteDialog(user.getId(), user.getUsername());
                return true;
            }
        });

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = users.get(i);
                user.setActiveUser(user);

                Intent newActivity = new Intent(UserLogin.this, TaskPage.class);
                startActivity(newActivity);

            }
        });

    }

    /**
     * This method updates the user list on start
     */
    @Override
    protected void onStart() {

        super.onStart();

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }

                UserList usersAdapter = new UserList(UserLogin.this,users);
                listViewUsers.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Dialog to allow users update or delete a user
     * @param userId
     * @param username
     */
    private void showUpdateDeleteDialog(final String userId, String username) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextUsername = (EditText) dialogView.findViewById(R.id.editTextUsername);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);
        final TextView title = (TextView) dialogView.findViewById(R.id.selfUsername);

        title.setText(username);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextUsername.getText().toString().trim();
                if (!TextUtils.isEmpty(name)) {
                    updateUser(userId, name);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(userId);
                b.dismiss();
            }
        });
    }

    /**
     * Dialog to allow users create an account
     */
    private void showNewUserDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_user_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText newTextUsername = (EditText) dialogView.findViewById(R.id.editTextUsername);
        final Button addButton = (Button) dialogView.findViewById(R.id.addButton);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser(newTextUsername.getText().toString().trim());
                b.dismiss();
            }
        });

    }

    /**
     * Method that updates database with user's updated information
     * @param id
     * @param username
     */
    private void updateUser(String id, String username) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users").child(id);

        User user = new User(id, username);

        dR.setValue(user);

        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
    }

    /**
     * this method deletes a user from database
     * @param id
     * @return
     */
    private boolean deleteUser(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();
        return true;
    }

    /**
     * This method adds a user to the database
     * @param name
     */
    private void addUser(String name) {
        String username = name;

        if(!TextUtils.isEmpty(username)){
            String id = databaseUsers.push().getKey();
            User user = new User(id, username);

            databaseUsers.child(id).setValue(user);

            Toast.makeText(this,"User added", Toast.LENGTH_LONG).show();
        } else{

            Toast.makeText(this,"Please enter a username", Toast.LENGTH_LONG).show();
        }

    }


}
