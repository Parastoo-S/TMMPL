package segproject.tmmpl;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_task_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewTaskName);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);
        TextView dueDateTime = (TextView) listViewItem.findViewById(R.id.dueDateTime);

        TextView status = (TextView) listViewItem.findViewById(R.id.status);


//        TextView textViewCreatorName = (TextView) listViewItem.findViewById(R.id.textViewCreatorName);
//        TextView textViewAssignedUserName = (TextView) listViewItem.findViewById(R.id.textViewAssignedUserName);


        Task task = tasks.get(position);
        textViewName.setText(task.getTaskName());
        textViewDescription.setText(task.getDescription());
        boolean completed = task.getStatus();
        if (completed){
            status.setText("Completed");
        }

        else{
            status.setText("Not Completed");
        }

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
