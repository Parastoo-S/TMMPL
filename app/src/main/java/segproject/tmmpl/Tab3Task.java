package segproject.tmmpl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Lori on 2017-12-01.
 */

public class Tab3Task extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_task_page, container, false);

        return rootView;
    }
    //Intent intent  = new Intent(Tab3Task.this, Shopping.class);
    //intent.putExtra("Fragmentone", 0); //pass zero for Fragmentone.
    //startActivity(intent);


}

