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

        Day twelveam = new Day("12am", "", "");
        Day oneam = new Day("1am", "", "");
        Day twoam = new Day("2am", "", "");
        Day threeam = new Day("3am", "", "");
        Day fouram = new Day("4am", "", "");
        Day fiveam = new Day("5am", "", "");
        Day sixam = new Day("6am", "", "");
        Day sevenam = new Day("7am", "", "");
        Day eightam = new Day("8m", "", "");
        Day nineam = new Day("9am", "", "");
        Day tenam = new Day("10am", "", "");
        Day elevenam = new Day("11am", "", "");
        Day twelvepm = new Day("12pm", "", "");
        Day onepm = new Day("1pm", "", "");
        Day twopm = new Day("2pm", "", "");
        Day threepm = new Day("3pm", "", "");
        Day fourpm = new Day("4pm", "", "");
        Day fivepm = new Day("5am", "", "");
        Day sixpm = new Day("6am", "", "");
        Day sevenpm = new Day("7am", "", "");
        Day eightpm = new Day("8am", "", "");
        Day ninepm = new Day("9am", "", "");
        Day tenpm = new Day("10am", "", "");
        Day elevenpm = new Day("11am", "", "");

        //add hour objects into arraylist
        ArrayList<Day> hourList = new ArrayList<>();
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
