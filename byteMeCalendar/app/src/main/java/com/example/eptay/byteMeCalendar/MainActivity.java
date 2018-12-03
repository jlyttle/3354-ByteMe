package com.example.eptay.byteMeCalendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /* MEMBER VARIABLES */
    private DrawerLayout m_drawerLayout;
    private CalendarView m_calendarView;
    private FloatingActionButton m_fab;
    private Calendar m_calendar = new GregorianCalendar();
    private SharedPreferences m_preferences;
    private TableLayout m_tableLayout;
    private EventCache m_eventCache;

    private int m_month = m_calendar.get(Calendar.MONTH);
    private int m_day = m_calendar.get(Calendar.DAY_OF_MONTH);
    private int m_year = m_calendar.get(Calendar.YEAR);
    private Day m_currentDay = new Day(m_year, m_month, m_day);

    /* METHODS */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        m_preferences = getPreferences(MODE_PRIVATE);

        //Retrieve event cache from shared preferences
        Gson gson = new Gson();
        String json = m_preferences.getString("EventCache", "");
        m_eventCache = gson.fromJson(json, EventCache.class);
        if (m_eventCache == null) {
            m_eventCache = EventCache.getInstance();
        }

        //Setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);

        //Instantiate each widget on the layout
        m_calendarView = findViewById(R.id.calendarView);
        m_fab = findViewById(R.id.floatingActionButton);
        m_drawerLayout = findViewById(R.id.drawer_layout);
        m_tableLayout = findViewById(R.id.tableLayout);

        //Create any event views that are in the cache
        List<Event> events = getOrderedEventList();
        drawEvents(events);

        //On changing the date, change the text to be new date
        m_calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                m_year = year;
                m_month = month;
                m_day = day;
                //setTitle("Date: " + (month + 1) + " / " + day + " / " + year);
                GlobalCalendar.setDay(m_year, m_month, m_day);
                m_currentDay = new Day(m_year, m_month, m_day);
                drawEvents(getOrderedEventList());
            }
        });

        //TODO: On clicking the action button, should change view to event adding form
        m_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventView.class));
            }
        });

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
                            case R.id.day_view:
                                //TODO: Fill out switch case for every activity in the drawer
                                //setContentView(R.layout.activity_dayviewactivity);
                                startActivity(new Intent(MainActivity.this, scrollingdayview.class));
                                break;
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

        /*m_tableLayout.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.event_context_menu, menu);
            }
        });*/
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor prefsEditor = m_preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(m_eventCache);
        prefsEditor.putString("EventCache", json);
        prefsEditor.commit();
    }

    private List<Event> getOrderedEventList() {
        List<Event> events = m_eventCache.get(m_currentDay);
        Collections.sort(events);
        return events;
    }

    private void drawEvents(List<Event> events) {
        //Draws all events to the tablelayout
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
        }
    }

    public void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        m_calendarView.setDate(GlobalCalendar.getInstance().getTimeInMillis());

        //Check to see if any events were added to current day and draw again
        List<Event> events = getOrderedEventList();
        drawEvents(events);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_context_menu, menu);

        //TODO Check to see if view clicked is a row. If it is, get the row and set it as the current context
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TableRow row = (TableRow) info.targetView;
        TextView eventIDView = (TextView) row.getChildAt(2);
        String eventID = eventIDView.getText().toString();
        Event selectedEvent = m_eventCache.find(eventID);

        switch (item.getItemId()) {
            case R.id.editMenuItem:
                return true;
            case R.id.deleteMenuItem:
                m_eventCache.remove(selectedEvent);
                return true;
            case R.id.shareMenuItem:
                return true;
        }
        return false;
    }
}
