package com.example.mahdi.takspage;

import android.app.Activity;
import android.provider.ContactsContract;
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

    private String[] fruitname;
    private String[] desc;
    private Integer[] imgid;
    private CheckBox checkBox;
    private Activity context;

    public CustomListview(Activity context, String[] fruitname, String[] desc, Integer[] imgid , CheckBox checkBox){
        super(context,R.layout.listview_layout,fruitname);

        this.context=context;
        this.fruitname=fruitname;
        this.desc=desc;
        this.imgid=imgid;
        this.checkBox=checkBox;

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


            viewHolder.ivw.setImageResource(imgid[position]);

            viewHolder.tvw1.setText(fruitname[position]);

            viewHolder.tvw2.setText(desc[position]);

            viewHolder.chb.setChecked(false);



        return r;
    }


    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        ImageView ivw;
        CheckBox chb;

        ViewHolder(View v){
            tvw1 = (TextView)v.findViewById(R.id.tvfruitname);
            tvw2 = (TextView)v.findViewById(R.id.tvdescription);
            ivw=(ImageView)v.findViewById(R.id.imageView);
            chb=(CheckBox)v.findViewById(R.id.checkBox);
        }

    }
}
