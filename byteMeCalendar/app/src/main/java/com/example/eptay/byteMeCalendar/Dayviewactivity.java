package com.example.eptay.byteMeCalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Dayviewactivity extends AppCompatActivity {

    private static final String TAG = "Dayviewactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayviewactivity);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listview);


        Time twelveam = new Time("12am", "", "");
        Time oneam = new Time("1am", "", "");
        Time twoam = new Time("2am", "", "");
        Time threeam = new Time("3am", "", "");
        Time fouram = new Time("4am", "", "");
        Time fiveam = new Time("5am", "", "");
        Time sixam = new Time("6am", "", "");
        Time sevenam = new Time("7am", "", "");
        Time eightam = new Time("8m", "", "");
        Time nineam = new Time("9am", "", "");
        Time tenam = new Time("10am", "", "");
        Time elevenam = new Time("11am", "", "");
        Time twelvepm = new Time("12pm", "", "");
        Time onepm = new Time("1pm", "", "");
        Time twopm = new Time("2pm", "", "");
        Time threepm = new Time("3pm", "", "");
        Time fourpm = new Time("4pm", "", "");
        Time fivepm = new Time("5am", "", "");
        Time sixpm = new Time("6am", "", "");
        Time sevenpm = new Time("7am", "", "");
        Time eightpm = new Time("8am", "", "");
        Time ninepm = new Time("9am", "", "");
        Time tenpm = new Time("10am", "", "");
        Time elevenpm = new Time("11am", "", "");

        //add hour objects into arraylist
        ArrayList<Time> hourList = new ArrayList<>();
        hourList.add(twelveam);
        hourList.add(oneam);
        hourList.add(twoam);
        hourList.add(threeam);
        hourList.add(fouram);
        hourList.add(fiveam);
        hourList.add(sixam);
        hourList.add(sevenam);
        hourList.add(eightam);
        hourList.add(nineam);
        hourList.add(tenam);
        hourList.add(elevenam);
        hourList.add(twelvepm);
        hourList.add(onepm);
        hourList.add(twopm);
        hourList.add(threepm);
        hourList.add(fourpm);
        hourList.add(fivepm);
        hourList.add(sixpm);
        hourList.add(sevenpm);
        hourList.add(eightpm);
        hourList.add(ninepm);
        hourList.add(tenpm);
        hourList.add(elevenpm);

        HourListAdapter adapter = new HourListAdapter(this, R.layout.activity_dayviewactivity, hourList);
        mListView.setAdapter(adapter);
    }
}
