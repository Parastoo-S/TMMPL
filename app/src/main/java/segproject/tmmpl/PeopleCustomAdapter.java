package segproject.tmmpl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by trist on 2017-11-22.
 */

public class PeopleCustomAdapter extends ArrayAdapter {
    private final Context context;
    private final ArrayList<User> myUsers;
    private final int[] avatars;
    private final ArrayList<Integer> completed;
    private final ArrayList<String> nextTasks;

    public PeopleCustomAdapter(Context context, ArrayList<User> userList, int[] avatarList, ArrayList<Integer> completedList, ArrayList<String> nextTaskList) {
        super(context, R.layout.people_task_layout, userList);
        this.context = context;
        this.myUsers = userList;
        this.avatars = avatarList;
        this.completed = completedList;
        this.nextTasks = nextTaskList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.people_task_layout, parent, false);

        TextView userNameTextField = (TextView) rowView.findViewById(R.id.userName);
        TextView taskCountTextField = (TextView) rowView.findViewById(R.id.taskCount);
        TextView nextTaskTextField = (TextView) rowView.findViewById(R.id.nextTask);
        ImageView userImage = (ImageView) rowView.findViewById(R.id.icon);

        userNameTextField.setText(myUsers.get(position).getUsername());
        taskCountTextField.setText("Allocated Tasks: " + completed.get(position));
        userImage.setImageResource(avatars[position % avatars.length]);
        nextTaskTextField.setText("Next Task: " + nextTasks.get(position));


        return rowView;
    }
}
