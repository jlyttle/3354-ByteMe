package com.example.eptay.byteMeCalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;



public class HourListAdapter extends ArrayAdapter<Time>{

    private static final String TAG = "HourListAdapter";
    private Context mContext;
    int mResourse;




    public HourListAdapter(Context context, int resource, ArrayList<Time> objects){
        super(context, resource, objects);
        mContext = context;
        mResourse =  resource;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       String hour = getItem(position).getHour();
       String title = getItem(position).getTitle();
       String desc = getItem(position).getDesc();

       Time time = new Time(hour, title, desc);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResourse, parent, false);

        TextView tvHour = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.textView3);

        tvHour.setText(hour);
        tvTitle.setText(title);
        tvDesc.setText(desc);

        return convertView;
    }
}
