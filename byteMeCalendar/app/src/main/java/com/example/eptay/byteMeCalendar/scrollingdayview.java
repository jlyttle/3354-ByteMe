package com.example.eptay.byteMeCalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Collections;
import java.util.List;

//scrollingdayview class
public class scrollingdayview extends AppCompatActivity {


    //variables
    private static final String TAG = "scrollingdayview";
    private static final int HOUR_HEIGHT = 61; //Each hour
    // in the scroll view is 61dp
    private ScrollView scrollView;
    private final int LEFT = 100;
    private final int RIGHT = 300;
    private EventCache m_eventCache = EventCache.getInstance();
    int currentYear = GlobalCalendar.getYear();
    int currentMonth = GlobalCalendar.getMonth();
    int currentDay = GlobalCalendar.getDayNum();
    Day selectedDay = new Day(currentYear, currentMonth, currentDay);
    String nameOfDay = selectedDay.getDayName();
    private LinearLayout m_linearLayout;

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
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
                for (Event event : prevEvents) {
                    double height = calculateHeightOfEvent(event);
                    double topMargin = calculateTimeDifference(event);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView textView = new TextView(scrollingdayview.this);
                    params.setMargins(LEFT, (int) topMargin, RIGHT, 50);
                    textView.setText(event.getName());
                    textView.setHeight((int) convertDpToPixel((float)height, getApplicationContext()));
                    textView.setBackgroundColor(Color.parseColor("#3F51B5"));
                    textView.setPadding(24, 0, 24, 0);
                    textView.setWidth((int) convertDpToPixel(100, getApplicationContext()));
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
                List<Event> nextEvents = EventCache.getInstance().get(nextDay);
                for (Event event : nextEvents) {
                    double height = calculateHeightOfEvent(event);
                    double topMargin = calculateTimeDifference(event);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView textView = new TextView(scrollingdayview.this);
                    params.setMargins(LEFT, (int) topMargin, RIGHT, 500);
                    textView.setText(event.getName());
                    textView.setHeight((int) convertDpToPixel((float)height, getApplicationContext()));
                    textView.setBackgroundColor(Color.parseColor("#000000"));
                    textView.setPadding(24, 0, 24, 0);
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
        double factor = (startMinute / 60) + startHour;
        return (factor * HOUR_HEIGHT);
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    private void drawEvents(List<Event> events) {
        for (Event event : events) {
            int index = 0;
            int startHour = event.getStartingHour();
            int startMin = event.getStartingMin();
            boolean PM = false;
            if (startHour > 11) {
                PM = true;
                if (startHour > 12) {
                    startHour -= 12;
                }
            } else if (startHour == 0) {
                startHour = 12;
            }
        }
    /*Draws all events to the tablelayout
    final int HEIGHT = 100;
    final int TIME_WIDTH = 200;
    final int TITLE_WIDTH = 500;

        m_tableLayout.removeAllViews();
        for (Event event: events) {
        int index = 0;
        int startHour = event.getStartingHour();
        int startMin = event.getStartingMin();
        boolean PM = false;
        if (startHour > 11) {
            PM = true;
            if (startHour > 12) {
                startHour -= 12;
            }
        }
        else if (startHour == 0) {
            startHour = 12;
        }
        String timeStr;
        if (startMin < 10) {
            timeStr = PM ? startHour + ":0" + startMin + " PM" : startHour + ":0" + startMin + " AM";
        }
        else {
            timeStr = PM ? startHour + ":" + startMin + " PM" : startHour + ":" + startMin + " AM";
        }
        TextView time = new TextView(this);
        time.setText(timeStr);
        TextView title = new TextView(this);
        title.setText(event.getName());
        TextView eventID = new TextView(this);
        eventID.setText(event.getID());
        eventID.setVisibility(View.INVISIBLE);
        TableRow row = new TableRow(this);
        row.addView(time, TIME_WIDTH, HEIGHT);
        row.addView(title, TITLE_WIDTH, HEIGHT);
        row.addView(eventID);
        m_tableLayout.addView(row, index);
        registerForContextMenu(row);
        ++index;
    }*/
    }
}
