package com.example.eptay.byteMeCalendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.List;

public class DayView extends AppCompatActivity {
    /* MEMBER VARIABLES */
    private static final String TAG = "DayView";
    private static final int HOUR_HEIGHT = 61; //Each hour in the scroll view is 61dp
    private ScrollView scrollView;
    private final int LEFT = 100;
    private final int RIGHT = 300;
    private EventCache m_eventCache = EventCache.getInstance();
    int currentYear = GlobalCalendar.getYear();
    int currentMonth = GlobalCalendar.getMonth();
    int currentDay = GlobalCalendar.getDayNum();
    Day selectedDay = new Day(currentYear, currentMonth, currentDay);
    String nameOfDay = selectedDay.getDayName();
    private RelativeLayout m_relativeLayout;

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        scrollView = findViewById(R.id.scrollView);
        m_relativeLayout = findViewById(R.id.eventLayout);

        drawEvents(getOrderedEventList());

        //TODO refactor
        String currentDate = (nameOfDay + ", " + getMonth(currentMonth + 1) + " " + currentDay + ", " + currentYear);
        final TextView textViewDate = findViewById(R.id.textViewDate);
        textViewDate.setTextColor(Color.parseColor("#FFFFFF"));
        textViewDate.setText(currentDate);

        scrollView.setOnTouchListener(new OnSwipeTouchListener(DayView.this) {
            public void onSwipeRight() {
                GlobalCalendar.setPrevDay();
                Day prevDay = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
                nameOfDay = prevDay.getDayName();
                currentMonth = prevDay.getMonth();
                currentDay = prevDay.getDayNum();
                currentYear = prevDay.getYear();
                String currentDate = (nameOfDay + ", " + getMonth(currentMonth + 1) + " " + currentDay + ", " + currentYear);
                textViewDate.setText(currentDate);
                m_relativeLayout.removeAllViews();
                List<Event> prevEvents = EventCache.getInstance().get(prevDay);
                drawEvents(prevEvents);
            }

            public void onSwipeLeft() {
                GlobalCalendar.setNextDay();
                Day nextDay = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
                nameOfDay = nextDay.getDayName();
                currentMonth = nextDay.getMonth();
                currentDay = nextDay.getDayNum();
                currentYear = nextDay.getYear();
                String currentDate = (nameOfDay + ", " + getMonth(currentMonth + 1) + " " + currentDay + ", " + currentYear);
                textViewDate.setText(currentDate);
                m_relativeLayout.removeAllViews();
                List<Event> nextEvents = EventCache.getInstance().get(nextDay);
                drawEvents(nextEvents);
            }
        });
        Log.d(TAG, "onCreate: Started.");
    }

    /**
     * This method takes in an event and calculates the height of the event object in dp
     *
     * @param event
     * @return factor * HOUR_HEIGHT
     */
    public double calculateHeightOfEvent(Event event) {
        int startHour = event.getStartingHour();
        int startMinute = event.getStartingMin();
        int endHour = event.getEndingHour();
        int endMinute = event.getEndingMin();
        double factor = (endHour - startHour) + ((endMinute - startMinute) / 60.0);

        return (factor * HOUR_HEIGHT);
    }

    private List<Event> getOrderedEventList() {
        List<Event> events = m_eventCache.get(selectedDay);
        Collections.sort(events);
        return events;
    }

    //method to determine difference from 12am to event start time
    public double calculateTimeDifference(Event event) {
        int startHour = event.getStartingHour();
        int startMinute = event.getStartingMin();
        double factor = (startMinute / 60.0) + startHour;
        return (factor * HOUR_HEIGHT);
    }

    private void drawEvents(List<Event> events) {
        for (Event event : events) {
            double height = calculateHeightOfEvent(event);
            double topMargin = calculateTimeDifference(event);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.topMargin = (int)ViewUtils.convertDpToPixel((float)topMargin, getApplicationContext());
            params.leftMargin = 240;
            TextView textView = new TextView(DayView.this);
            textView.setLayoutParams(params);
            textView.setText(event.getName());
            textView.setHeight((int)ViewUtils.convertDpToPixel((float)height, getApplicationContext()));
            textView.setBackgroundColor(Color.parseColor("#C75B12"));
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            textView.setPadding(24, 0, 24, 0);
            textView.setWidth((int)ViewUtils.convertDpToPixel(200, getApplicationContext()));
            textView.setGravity(0x11);
            m_relativeLayout.addView(textView);
        }
    }
}
