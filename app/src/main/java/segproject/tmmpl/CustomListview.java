package segproject.tmmpl;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Mahdi on 11/23/2017.
 */

public class CustomListview extends ArrayAdapter<String> {

    private String[] taskName;
    private String[] taskDescription;
    private Integer[] userProfileImage;
    private CheckBox isComplete;
    private Activity context;

    public CustomListview(Activity context, String[] taskName, String[] taskDescription, Integer[] userProfileImage , CheckBox isComplete){
        super(context,R.layout.listview_layout,taskName);

        this.context=context;
        this.taskName=taskName;
        this.taskDescription=taskDescription;
        this.userProfileImage=userProfileImage;
        this.isComplete=isComplete;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View r=convertView;
        ViewHolder viewHolder =null;

        if(r==null){

            LayoutInflater layoutInflater = context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.listview_layout,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);


        }else{
            viewHolder=(ViewHolder)r.getTag();
        }


        viewHolder.ivw.setImageResource(userProfileImage[position]);

        viewHolder.tvw1.setText(taskName[position]);

        viewHolder.tvw2.setText(taskDescription[position]);

        viewHolder.chb.setChecked(false);



        return r;
    }


    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        ImageView ivw;
        CheckBox chb;

        ViewHolder(View v){
            tvw1 = (TextView)v.findViewById(R.id.taskNameTextView);
            tvw2 = (TextView)v.findViewById(R.id.taskDescriptionTextView);
            ivw=(ImageView)v.findViewById(R.id.userImageView);
            chb=(CheckBox)v.findViewById(R.id.isCompleteCB);
        }

    }
}
