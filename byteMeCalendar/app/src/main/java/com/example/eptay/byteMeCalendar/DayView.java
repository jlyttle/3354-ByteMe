package com.example.eptay.byteMeCalendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    private View m_currentContextView;
    private Event m_selectedEvent = null;
    private final int EDIT_EVENT = 0;
    private EventCache m_eventCache = EventCache.getInstance();
    int currentYear = GlobalCalendar.getYear();
    int currentMonth = GlobalCalendar.getMonth();
    int currentDay = GlobalCalendar.getDayNum();
    Day selectedDay = new Day(currentYear, currentMonth, currentDay);
    String nameOfDay = selectedDay.getDayName();
    private RelativeLayout m_relativeLayout;


    /**
     * This method takes in a month and returns it in a specific format
     * @param month
     * @return month symbol
     */
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
            //method for swipe right gesture
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

            //method for swipeleft gesture
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

    /**
     * This method gets events from selected day, then sorts them by hour
     * @return events from ordered list
     */
    private List<Event> getOrderedEventList() {
        List<Event> events = m_eventCache.get(selectedDay);
        Collections.sort(events);
        return events;
    }

    /**
     * This method takes in an event and calculates the difference from 12am to the given event's start time
     * @param event
     * @return factor * HOUR_HEIGHT
     */
    public double calculateTimeDifference(Event event) {
        int startHour = event.getStartingHour();
        int startMinute = event.getStartingMin();
        double factor = (startMinute / 60.0) + startHour;
        return (factor * HOUR_HEIGHT);
    }

    /**
     * This void method is used to create the context menu that contains edit, delete, and share
     * @param menu
     * @param v
     * @param menuInfo
     * @return void
     */
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_context_menu, menu);
        m_currentContextView = v;
    }
    /**
     * This method takes in an item and does various things depending on if the user selects edit, delete, or share
     * @param item
     * @return boolean
     */
    public boolean onContextItemSelected(MenuItem item) {
        if (m_currentContextView != null) {
            TextView textView = (TextView) m_currentContextView;
            textView.getTag();
            String id = (String) textView.getTag();
            m_selectedEvent = m_eventCache.find(id);
            Intent intent;

            switch (item.getItemId()) {
                case R.id.editMenuItem:
                    intent = new Intent(DayView.this, EventView.class);
                    intent.putExtra("title", m_selectedEvent.getName());
                    intent.putExtra("description", m_selectedEvent.getDescription());
                    intent.putExtra("startHour", m_selectedEvent.getStartingHour());
                    intent.putExtra("startMin", m_selectedEvent.getStartingMin());
                    intent.putExtra("endHour", m_selectedEvent.getEndingHour());
                    intent.putExtra("endMin", m_selectedEvent.getEndingMin());
                    startActivityForResult(intent, EDIT_EVENT);
                    return true;
                case R.id.deleteMenuItem:
                    m_eventCache.remove(m_selectedEvent);
                    drawEvents(getOrderedEventList());
                    return true;
                case R.id.shareMenuItem:
                    intent = new Intent(DayView.this, ShareView.class);
                    intent.putExtra("eventID", m_selectedEvent.getID());
                    startActivity(intent);
                    return true;
                default:
                    m_currentContextView = null;
                    return false;
            }

        }
        return false;
    }

    /**
     * This method takes in a list of events and draws the event box
     * @param events
     * @return void
     */
    private void drawEvents(List<Event> events) {
        m_relativeLayout.removeAllViews();
        for (Event event : events) {
            double height = calculateHeightOfEvent(event);
            double topMargin = calculateTimeDifference(event);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            params.topMargin = (int) ViewUtils.convertDpToPixel((float) topMargin, getApplicationContext());
            params.leftMargin = 240;
            TextView textView = new TextView(DayView.this);
            textView.setLayoutParams(params);
            textView.setText(event.getName());
            textView.setTag(event.getID());
            textView.setHeight((int) ViewUtils.convertDpToPixel((float) height, getApplicationContext()));
            switch (event.getCategory()) {
                case NONE:
                    textView.setBackgroundColor(Color.parseColor("#000000"));
                    break;
                case BLUE:
                    textView.setBackgroundColor(Color.parseColor("#0000FF"));
                    break;
                case ORANGE:
                    textView.setBackgroundColor(Color.parseColor("#C75B12"));
                    break;
                case GREEN:
                    textView.setBackgroundColor(Color.parseColor("#008542"));
                    break;
                case YELLOW:
                    textView.setBackgroundColor(Color.parseColor("#999900"));
                    break;
                case RED:
                    textView.setBackgroundColor(Color.parseColor("#FF0000"));
                    break;
                case PURPLE:
                    textView.setBackgroundColor(Color.parseColor("#800080"));
                    break;
            }
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            textView.setPadding(24, 0, 24, 0);
            textView.setWidth((int) ViewUtils.convertDpToPixel(200, getApplicationContext()));
            textView.setGravity(0x11);
            m_relativeLayout.addView(textView);
            registerForContextMenu(textView);
        }
    }

    @Override
    /**
     * This method edits the event by requesting the updated request and deleting the old event
     * @param requestCode
     * @param resultCode
     * @param data
     * @return void
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EDIT_EVENT:
                if (resultCode == Activity.RESULT_OK) {
                    m_eventCache.remove(m_selectedEvent);
                    drawEvents(getOrderedEventList());
                }
                break;
        }
    }
}
