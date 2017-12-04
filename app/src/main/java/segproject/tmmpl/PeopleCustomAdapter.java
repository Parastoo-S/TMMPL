package segproject.tmmpl;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by trist on 2017-11-22.
 */

public class PeopleCustomAdapter extends ArrayAdapter {
    private final Activity context;
    private final ArrayList<User> myUsers;
    private final ArrayList<Task> myTasks;
    int[] avatars = {
            R.mipmap.i1,
            R.mipmap.i2,
            R.mipmap.i6,
            R.mipmap.i3,
            R.mipmap.i4,
            R.mipmap.i5,
    };
    ArrayList<Integer> taskCount = new ArrayList<>();
    ArrayList<String> nextTaskName = new ArrayList<>();
    ArrayList<String> taskIds = new ArrayList<>();
    DatabaseReference databaseUserTasks;
//    private final ArrayList<Integer> completed;
//    private final ArrayList<String> nextTasks;
    final ArrayList<Task> tasks = new ArrayList<>();
    public PeopleCustomAdapter(Activity context, ArrayList<User> users,ArrayList<Task> tasks ){
        super(context, R.layout.people_task_layout, users);
        this.context = context;
        this.myUsers = users;
        this.myTasks = tasks;
    }

//    public PeopleCustomAdapter(Activity context, ArrayList<User> userList, ArrayList<Integer> completedList, ArrayList<String> nextTaskList) {
//        super(context, R.layout.people_task_layout, userList);
//        this.context = context;
//        this.myUsers = userList;
//        this.completed = completedList;
//        this.nextTasks = nextTaskList;
//    }

    public View getView(int position, View convertView, ViewGroup parent) {
//       LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(R.layout.people_task_layout, parent, false);

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.people_task_layout, null, true);


        for(User user : myUsers) {

            databaseUserTasks = FirebaseDatabase.getInstance().getReference("users").child(user.getId()).child("assignedTaskIds");

            databaseUserTasks.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    taskIds.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        taskIds.add(postSnapshot.getValue().toString());

                    }
                    taskCount.add(taskIds.size());
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }


        taskCount.add(2);
        taskCount.add(2);
        taskCount.add(2);
        taskCount.add(2);
        taskCount.add(2);
        taskCount.add(2);


        TextView userNameTextField = (TextView) rowView.findViewById(R.id.userName);
        TextView taskCountTextField = (TextView) rowView.findViewById(R.id.taskCount);
        TextView nextTaskTextField = (TextView) rowView.findViewById(R.id.nextTask);
        ImageView userImage = (ImageView) rowView.findViewById(R.id.icon);

        userNameTextField.setText(myUsers.get(position).getUsername());
        taskCountTextField.setText("Allocated Tasks: " + taskCount.get(position));
        userImage.setImageResource(avatars[position% avatars.length]);
        Task task = myTasks.get(position % myTasks.size());
        nextTaskTextField.setText("Next Task: " + task.getTaskName());


        return rowView;
    }
}
