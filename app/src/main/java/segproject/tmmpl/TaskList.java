package segproject.tmmpl;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class TaskList extends ArrayAdapter<Task> {
    private Activity context;
    List<Task> tasks;

    public TaskList(Activity context, List<Task> tasks) {
        super(context, R.layout.layout_task_list, tasks);
        this.context = context;
        this.tasks = tasks;
    }
    public void onCheckBox(View v){

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_task_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewTaskName);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);
        TextView dueDateTime = (TextView) listViewItem.findViewById(R.id.dueDateTime);

        final TextView status = (TextView) listViewItem.findViewById(R.id.status);

        final CheckBox completed = (CheckBox)listViewItem.findViewById(R.id.completed);
        ImageView profilePicImage = (ImageView)listViewItem.findViewById(R.id.profilePicImage);

        completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(completed.isChecked()==false){
                    status.setText("incomplete");

                }

                if(completed.isChecked()==true) {
                    status.setText("Complete");
                    Toast.makeText(getContext(), "Task Completed",
                            Toast.LENGTH_LONG).show();

                }
            }
        });




//        TextView textViewCreatorName = (TextView) listViewItem.findViewById(R.id.textViewCreatorName);
//        TextView textViewAssignedUserName = (TextView) listViewItem.findViewById(R.id.textViewAssignedUserName);


        Task task = tasks.get(position);
        textViewName.setText(task.getTaskName());
        textViewDescription.setText(task.getDescription());
     /*   boolean completed = task.getStatus();
        if (completed){
            status.setText("Completed");
        }

        else{
            status.setText("Not Completed");
        }
*/
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
