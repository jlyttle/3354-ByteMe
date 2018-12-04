package com.example.eptay.byteMeCalendar;

import android.annotation.SuppressLint;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormatSymbols;


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
    private final int LEFT = 100;
    private final int RIGHT = 500;
    private LinearLayout m_linearLayout;

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollingdayview);
        scrollView = findViewById(R.id.scrollView);
        m_linearLayout = findViewById(R.id.eventLayout);

       // View m_calendarView = findViewById(R.id.calendarView);

        //TODO refactor
        //Calendar calendar = Calendar.getInstance();
        Day selectedDay = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
        int currentYear = GlobalCalendar.getYear();
        int currentMonth = GlobalCalendar.getMonth();
        int currentDay = GlobalCalendar.getDayNum();
        String nameOfDay = selectedDay.getDayName();
        String currentDate = (nameOfDay + ", " + getMonth(currentMonth + 1) + " " + currentDay + ", " + currentYear);
        //String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setText(currentDate);

        scrollView.setOnTouchListener(new OnSwipeTouchListener(scrollingdayview.this) {
            public void onSwipeRight() {
                GlobalCalendar.setPrevDay();
                m_linearLayout.removeAllViews();
                Day prevDay = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
                List<Event> prevEvents = EventCache.getInstance().get(prevDay);
                for (Event event: prevEvents) {
                    double height = calculateHeightOfEvent(event);
                    double topMargin = calculateTimeDifference(event);

                    //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView textView = new TextView(scrollingdayview.this);
                    //params.setMargins(LEFT, (int) topMargin, RIGHT, 50);
                    textView.setText(event.getName());
                    textView.setHeight((int) height);
                    m_linearLayout.addView(textView);
                }

                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(GlobalCalendar.getInstance().getTime());
                TextView textViewDate = findViewById(R.id.textViewDate);
                textViewDate.setText(currentDate);
                Toast.makeText(scrollingdayview.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                GlobalCalendar.setNextDay();
                m_linearLayout.removeAllViews();
                Day nextDay = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
                List <Event> nextEvents = EventCache.getInstance().get(nextDay);
                for (Event event: nextEvents) {
                    double height = calculateHeightOfEvent(event);
                    double topMargin = calculateTimeDifference(event);

                    //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView textView = new TextView(scrollingdayview.this);
                    //params.setMargins(LEFT, (int) topMargin, RIGHT, 500);
                    textView.setText(event.getName());
                    textView.setHeight((int) height);
                    m_linearLayout.addView(textView);
                }

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

    //Method to calculate the height of the event object in dp
    public double calculateHeightOfEvent(Event event) {
        int startHour = event.getStartingHour();
        int startMinute = event.getStartingMin();
        int endHour = event.getEndingHour();
        int endMinute = event.getEndingMin();
        double factor = (endHour - startHour) + ((endMinute - startMinute) / 60.0);

        return (factor * HOUR_HEIGHT);
    }

    //method to determine difference from 12am to event start time
    public double calculateTimeDifference(Event event){
        int startHour = event.getStartingHour();
        int startMinute = event.getStartingMin();
        double factor = (startMinute / 60) + startHour;
        return (factor * HOUR_HEIGHT);
    }
}
