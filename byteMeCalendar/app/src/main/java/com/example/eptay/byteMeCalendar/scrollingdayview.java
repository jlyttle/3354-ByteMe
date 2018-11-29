package com.example.eptay.byteMeCalendar;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;



import java.util.List;

public class scrollingdayview extends AppCompatActivity{

    private static final String TAG = "scrollingdayview";
    private static final int HOUR_HEIGHT = 61; //Each hour in the scroll view is 61dp
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollingdayview);
        scrollView = findViewById(R.id.scrollView);
        Log.d(TAG, "onCreate: Started.");



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

    //method to determine difference from 12am to event start time
    public double calculateTimeDifference(Event event){
        int startHour = event.getStartingHour();
        int startMinute = event.getStartingMinute();
        int endHour = event.getEndingHour();
        int endMinute = event.getEndingMinute();
        double factor = (startMinute/60) + startHour;
                return (factor * HOUR_HEIGHT);
    }


scrollView.setOnTouchListener(new OnSwipeTouchListener(context) {
        @Override
        public void onSwipeLeft() {

        }
    });

}
