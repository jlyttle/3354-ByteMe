package com.example.eptay.byteMeCalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WeekListAdapter extends ArrayAdapter<Day> {

    private static final String TAG = "WeekListAdapter";
    private Context mContext;
    int mResource;

    public WeekListAdapter(Context context, int resource, ArrayList<Day> objects ) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @Override
    @NonNull
    public View getView(int position , View convertView , ViewGroup parent) {

        String dayName = getItem(position).getDayName();
        String date = getItem(position).getDate();
        int eventCount = getItem(position).getEventCount();
        String stringEventCount = Integer.toString(eventCount);
        Day day = new Day(dayName,date,eventCount);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvDayName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvEventCount = (TextView) convertView.findViewById(R.id.textView3);

        tvDayName.setText(dayName);
        tvDate.setText(date);
        tvEventCount.setText(stringEventCount);

        return convertView;
    }
}
