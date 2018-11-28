package com.example.eptay.byteMeCalendar;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class Dayviewactivity extends AppCompatActivity {

    private static final String TAG = "Dayviewactivity";
    private static final int HOUR_HEIGHT = 61; //Each hour in the scroll view is 60dp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayviewactivity);
        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.listview);
        ConstraintLayout m_constraintLayout = findViewById(R.id.activity_dayviewactivity);

        Day day = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
        EventCache eventCache = EventCache.getInstance();
        List<Event> eventArrayList = eventCache.get(day);
        for (Event event : eventArrayList) {

        }

    }

    public double calculateHeightOfEvent(Event event) {
        int startHour = event.getStartingHour();
        int startMinute = event.getStartingMinute();
        int endHour = event.getEndingHour();
        int endMinute = event.getEndingMinute();
        double factor = (endHour - startHour) + ((endMinute - startMinute) / 60.0);

        return (factor * HOUR_HEIGHT);
    }
}
