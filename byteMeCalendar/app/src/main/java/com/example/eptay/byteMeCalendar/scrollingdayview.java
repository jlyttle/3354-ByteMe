package com.example.eptay.byteMeCalendar;

import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class scrollingdayview extends AppCompatActivity{

    private static final String TAG = "scrollingdayview";
    private static final int HOUR_HEIGHT = 61; //Each hour
    // in the scroll view is 61dp
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollingdayview);
        scrollView = findViewById(R.id.scrollView);

        //TODO refactor
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText(currentDate);

        scrollView.setOnTouchListener(new OnSwipeTouchListener(scrollingdayview.this) {
            public void onSwipeRight() {

                GlobalCalendar.setPrevDay();
                Day prevDay = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
                List<Event> nextEvents = EventCache.getInstance().get(prevDay);
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(GlobalCalendar.getInstance().getTime());
                TextView textViewDate = findViewById(R.id.textViewDate);
                textViewDate.setText(currentDate);
                Toast.makeText(scrollingdayview.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                GlobalCalendar.setNextDay();
                Day nextDay = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
                List <Event> prevEvents = EventCache.getInstance().get(nextDay);
                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(GlobalCalendar.getInstance().getTime());
                TextView textViewDate = findViewById(R.id.textViewDate);
                textViewDate.setText(currentDate);
                Toast.makeText(scrollingdayview.this, "right", Toast.LENGTH_SHORT).show();

            }
        });
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
}
