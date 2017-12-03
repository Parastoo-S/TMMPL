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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserLogin extends AppCompatActivity {
    EditText editTextUsername;

    EditText editAvatartId;
    Button buttonAddUser;
    ListView listViewUsers;
    DatabaseReference databaseUsers;
    List<User> users;

    private DrawerLayout nDrawerLayout;
    private ActionBarDrawerToggle nToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        nDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        nToggle = new ActionBarDrawerToggle(this, nDrawerLayout, R.string.open ,R.string.close );

        nDrawerLayout.addDrawerListener(nToggle);
        nToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
//        editAvatartId = (EditText) findViewById(R.id.editAvatartId);

        listViewUsers = (ListView) findViewById(R.id.listViewUsers);
        buttonAddUser = (Button) findViewById(R.id.newUser);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        users = new ArrayList<>();

        //adding an onclicklistener to button
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



    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_user_login, menu);
//        return true;
//    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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

    private void showUpdateDeleteDialog(final String userId, String username) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextUsername = (EditText) dialogView.findViewById(R.id.editTextUsername);
//        final EditText editTextPrice  = (EditText) dialogView.findViewById(R.id.editTextPrice);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);
        final TextView title = (TextView) dialogView.findViewById(R.id.selfUsername);
//        final ImageView avatar = (ImageView) dialogView.findViewById(R.id.editAvatar);

//        dialogBuilder.setTitle(username);
        title.setText(username);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextUsername.getText().toString().trim();
//                double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));
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

    private void showNewUserDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.new_user_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText newTextUsername = (EditText) dialogView.findViewById(R.id.editTextUsername);
//        final EditText editTextPrice  = (EditText) dialogView.findViewById(R.id.editTextPrice);
        final Button addButton = (Button) dialogView.findViewById(R.id.addButton);
//        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);

//        dialogBuilder.setTitle(username);
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


    private void updateUser(String id, String username) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users").child(id);

        User user = new User(id, username);

        dR.setValue(user);

        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
    }


    private boolean deleteUser(String id) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("users").child(id);
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


    private void addUser(String name) {
        String username = name;
//        double price=Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));

        if(!TextUtils.isEmpty(username)){
            String id = databaseUsers.push().getKey();
            User user = new User(id, username);

            databaseUsers.child(id).setValue(user);

//                .setText("");
//            editTextPrice.setText("");
            Toast.makeText(this,"User added", Toast.LENGTH_LONG).show();
        } else{

            Toast.makeText(this,"Please enter a username", Toast.LENGTH_LONG).show();
        }

    }


    public void goToQuickAccess(View view){
        Intent startNewActivity = new Intent(this, QuickAccess.class);
        startActivity(startNewActivity);
    }

}
