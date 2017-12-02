package segproject.tmmpl;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.media.Image;
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

public class UserLogin extends AppCompatActivity {
    EditText editTextUsername;

    EditText editAvatartId;
    Button buttonAddUser;
    ListView listViewUsers;
    DatabaseReference databaseUsers;
    List<User> users;
    ImageView buttonSetAvatar;
    Spinner role;


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


        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
//        editAvatartId = (EditText) findViewById(R.id.editAvatartId);

        buttonSetAvatar = (ImageView) findViewById(R.id.avatar);

        listViewUsers = (ListView) findViewById(R.id.listViewUsers);
        buttonAddUser = (Button) findViewById(R.id.newUser);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        users = new ArrayList<>();
       // role = (Spinner) findViewById(R.id.role);



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

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = users.get(i);
                //User currentUser = Singleton.getInstance(user);
                user.setActiveUser(user);

//                Toast.makeText(getApplicationContext(), currentUser.getUsername(), Toast.LENGTH_LONG).show();
                Intent newActivity = new Intent(UserLogin.this, TaskPage.class);
//                newActivity.putExtra("activeUser", user);
                startActivity(newActivity);

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
        // TODO: FiX this
//        buttonSetAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectAvatar();
//            }
//        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser(newTextUsername.getText().toString().trim());
                b.dismiss();
            }
        });

    }
//  // TODO: tifosdlfnjds, 
//    private void selectAvatar(){
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View dialogView2 = inflater.inflate(R.layout.activity_set_avatar, null);
//        dialogBuilder.setView(dialogView2);
//
//        String avatarName = "i1";
//        switch (data.getIntExtra("imageID",R.id.i1)) {
//            case R.id.i1:
//                avatarName = "i1";
//                break;
//            case R.id.i2:
//                avatarName = "i2";
//                break;
//            case R.id.i3:
//                avatarName = "i3";
//                break;
//            case R.id.i4:
//                avatarName = "i4";
//                break;
//            case R.id.i5:
//                avatarName = "i5";
//                break;
//            case R.id.i6:
//                avatarName = "i6";
//                break;
//            default:
//                avatarName = "i1";
//                break;
//        }
//    }


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

}
