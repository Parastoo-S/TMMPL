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

import java.util.ArrayList;
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
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_task_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewTaskName);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.textViewDescription);
        TextView dueDateTime = (TextView) listViewItem.findViewById(R.id.dueDateTime);

        final TextView status = (TextView) listViewItem.findViewById(R.id.status);

        final CheckBox completed = (CheckBox)listViewItem.findViewById(R.id.completed);
        ImageView profilePicImage = (ImageView)listViewItem.findViewById(R.id.profilePicImage);
        Button statusBtn = (Button)listViewItem.findViewById(R.id.statusBtn);
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

        statusBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.radiobutton_dialog);
                List<String> stringList = new ArrayList<>();  // here is list
                                   /*  for (int i = 0; i < 5; i++) {
                                         stringList.add("RadioButton " + (i + 1));
                                     }*/
                stringList.add("Complete");
                stringList.add("Inomplete");
                stringList.add("Defferred");

                RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

                for (int i = 0; i < stringList.size(); i++) {
                    RadioButton rb = new RadioButton(context); // dynamically creating RadioButton and adding to RadioGroup.
                    rb.setText(stringList.get(i));
                    rg.addView(rb);
                }

                dialog.show();
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
