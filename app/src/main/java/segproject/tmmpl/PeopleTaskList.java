package segproject.tmmpl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class PeopleTaskList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_task_list);

        String[] choreList = {"Alex", "Rex", "Alise", "Jack"};
        int[] avatarList = {R.drawable.i1, R.drawable.i2, R.drawable.i6, R.drawable.i3};
        String[] completedList = {"0", "3", "1", "10"};
        String[] nextTaskList = {"Clean up kitchen", "Take out garbage", "----", "Buy new TV",};
        ListView listView = (ListView) findViewById(R.id.list);

        PeopleCustomAdapter adapter = new PeopleCustomAdapter(this, choreList, avatarList, completedList, nextTaskList);
        listView.setAdapter(adapter);
    }
}
