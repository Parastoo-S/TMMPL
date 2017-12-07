package segproject.tmmpl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class is the adapter for the main task page
 */
public class TaskList extends ArrayAdapter<Task> {
    private Activity context;
    List<Task> tasks;
    Task clickedTask;
    DatabaseReference databaseTasks;

    //Constructor
    public TaskList(Activity context, List<Task> tasks) {
        super(context, R.layout.layout_task_list, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    /**
     * This method sets the adapter items
     * @param position
     * @param convertView
     * @param parent
     * @return
     */

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final Task clickedTask = tasks.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_task_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewTaskName);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);
        TextView dueDateTime = (TextView) listViewItem.findViewById(R.id.dueDateTime);

        final TextView status = (TextView) listViewItem.findViewById(R.id.status);

        final CheckBox completed = (CheckBox)listViewItem.findViewById(R.id.completed);
        completed.setFocusable(false);
        ImageView profilePicImage = (ImageView)listViewItem.findViewById(R.id.profilePicImage);
        completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            /**
             * Sets the status of a tasks
             * @param buttonView
             * @param isChecked
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(completed.isChecked()==false){
                    clickedTask.setStatus(false);
                    status.setText("Incomplete");
                }

                if(completed.isChecked()==true) {
                    clickedTask.setStatus(true);
                    status.setText("Complete");
                    Toast.makeText(getContext(), "Task Completed",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });



        Task task = tasks.get(position);
        textViewName.setText(task.getTaskName());
        textViewDescription.setText(task.getDescription());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(task.getDueDate());

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        dueDateTime.setText("Deadline: " + mYear + "/" + mMonth + "/" + mDay + "at "+ mHour + ":" + mMinute);

        return listViewItem;
    }



}
