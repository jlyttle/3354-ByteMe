package com.example.eptay.byteMeCalendar;

import android.content.Intent;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
    private final int LEFT = 100;
    private final int RIGHT = 300;
    private DrawerLayout m_drawerLayout;
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
                List<Event> prevEvents = EventCache.getInstance().get(prevDay);
                for (Event event: prevEvents) {
                    double height = calculateHeightOfEvent(event);
                    double topMargin = calculateTimeDifference(event);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView textView = new TextView(scrollingdayview.this);
                    params.setMargins(LEFT, (int) topMargin, RIGHT, 50);
                    textView.setText(event.getName());
                }

                String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(GlobalCalendar.getInstance().getTime());
                TextView textViewDate = findViewById(R.id.textViewDate);
                textViewDate.setText(currentDate);
                Toast.makeText(scrollingdayview.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                GlobalCalendar.setNextDay();
                Day nextDay = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
                List <Event> nextEvents = EventCache.getInstance().get(nextDay);
                for (Event event: nextEvents) {
                    double height = calculateHeightOfEvent(event);
                    double topMargin = calculateTimeDifference(event);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    TextView textView = new TextView(scrollingdayview.this);
                    params.setMargins(LEFT, (int) topMargin, RIGHT, 50);
                    textView.setText(event.getName());
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        // close drawer when item is tapped
                        m_drawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {

                            case R.id.month_view:
                                startActivity(new Intent(scrollingdayview.this, MainActivity.class));
                                break;
                            case R.id.day_view:
                                //TODO: Fill out switch case for every activity in the drawer
                                //setContentView(R.layout.activity_dayviewactivity);
                                //startActivity(new Intent(scrollingdayview.this, scrollingdayview.class));
                                //break;
                            case R.id.week_view:
                                //TODO: Fill out switch case for every activity in the drawer
                                //startActivity(new Intent(MainActivity.this, ActivityName.class));
                                break;

                            default:
                                break;
                        }

                        return true;
                    }
                });

        navigationView.getMenu().getItem(0).setChecked(true);
    }



    //Method to calculate the height of the event object in dp
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
        double factor = (startMinute / 60) + startHour;
        return (factor * HOUR_HEIGHT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                m_drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
    }
}
