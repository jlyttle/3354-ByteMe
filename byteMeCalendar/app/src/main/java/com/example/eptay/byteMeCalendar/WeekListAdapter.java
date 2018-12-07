package com.example.eptay.byteMeCalendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*
    Class used to format information that will be passed into an adapter for the weeklist. 
*/

public class WeekListAdapter extends ArrayAdapter<Day> {

    /* MEMBER VARIABLES */
    private static final String TAG = "WeekListAdapter";
    private Context m_Context;
    private int m_Resource;

    /* METHODS */

    public WeekListAdapter(Context context, int resource, ArrayList<Day> objects ) {
        super(context, resource, objects);
        m_Context = context;
        m_Resource = resource;
    }

    @Override
    @NonNull
    /**
     * This method gets the view
     * @param position
     * @param convertView
     * @param parent
     * @return convertView
     */
    public View getView(int position , View convertView , ViewGroup parent) {
        String dayName = getItem(position).getDayName();
        String date = getItem(position).getDate();
        int eventCount = getItem(position).getEventCount();
        String stringEventCount = Integer.toString(eventCount);
        //Day day = new Day(dayName,date,eventCount);

        LayoutInflater inflater = LayoutInflater.from(m_Context);
        convertView = inflater.inflate(m_Resource,parent,false);

        TextView tvDayName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView2);
        //TextView tvEventCount = (TextView) convertView.findViewById(R.id.textView3);

        tvDayName.setText(dayName);
        tvDate.setText(date);
        //tvEventCount.setText(stringEventCount);

        return convertView;
    }
}
