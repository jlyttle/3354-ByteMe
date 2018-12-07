package com.example.eptay.byteMeCalendar;

import android.app.Activity;
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
import android.widget.CalendarView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /* MEMBER VARIABLES */
    private DrawerLayout m_drawerLayout;
    private CalendarView m_calendarView;
    private FloatingActionButton m_fab;
    private SharedPreferences m_preferences;
    private TableLayout m_tableLayout;
    private NavigationView m_navigationView;
    private EventCache m_eventCache;
    private View m_currentContextView = null;
    private Event m_selectedEvent = null;

    private int m_day = GlobalCalendar.getDayNum();
    private int m_month = GlobalCalendar.getMonth();
    private int m_year = GlobalCalendar.getYear();
    private Day m_currentDay = new Day(m_year, m_month, m_day);
    private final int EDIT_EVENT = 0;
    private final int SHARE_EVENT = 1;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.menu);

        //Instantiate each widget on the layout
        m_calendarView = findViewById(R.id.calendarView);
        m_fab = findViewById(R.id.floatingActionButton);
        m_drawerLayout = findViewById(R.id.drawer_layout);
        m_tableLayout = findViewById(R.id.tableLayout);
        m_navigationView = findViewById(R.id.nav_view);

        //Create any event views that are in the cache
        drawEvents(getOrderedEventList());

        //On changing the date, set the date in the global calendar and draw any events
        m_calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                m_year = year;
                m_month = month;
                m_day = day;
                GlobalCalendar.setDay(m_year, m_month, m_day);
                m_currentDay = new Day(m_year, m_month, m_day);
                drawEvents(getOrderedEventList());
            }
        });

        //Go to the event add view when clicking on the action button
        m_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EventView.class));
            }
        });

        //Set menu items in the navigation view to start their respective activities
        m_navigationView.bringToFront();
        m_navigationView.getMenu().getItem(0).setChecked(true);
        m_navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //Set item as selected to persist highlight
                        menuItem.setChecked(true);

                        //Close drawer when item is tapped
                        m_drawerLayout.closeDrawers();

                        switch (menuItem.getItemId()) {
                            case R.id.day_view:
                                startActivity(new Intent(MainActivity.this, DayView.class));
                                break;
                            case R.id.week_view:
                                startActivity(new Intent(MainActivity.this, WeekView.class));
                                break;
                            default:
                                break;
                        }

                        return true;
                    }
                });
    }

    //Open the navigation drawer when the menu is clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                m_drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //On closing the app, save the event cache to storage
    @Override
    protected void onDestroy() {
        //TODO Check if this works
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
        final int HEIGHT = (int)ViewUtils.convertDpToPixel(25, MainActivity.this);
        final int TIME_WIDTH = (int)ViewUtils.convertDpToPixel(100, MainActivity.this);
        final int TITLE_WIDTH = (int)ViewUtils.convertDpToPixel(350, MainActivity.this);

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
        m_currentDay = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());

        //Check to see if any events were added to current day and draw again
        List<Event> events = getOrderedEventList();
        drawEvents(events);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_context_menu, menu);

        //TODO Check to see if view clicked is a row. If it is, get the row and set it as the current context
        m_currentContextView = v;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (m_currentContextView != null) {
            TableRow row = (TableRow) m_currentContextView;
            TextView eventIDView = (TextView) row.getChildAt(2);
            String eventID = eventIDView.getText().toString();
            m_selectedEvent = m_eventCache.find(eventID);

            switch (item.getItemId()) {
                case R.id.editMenuItem:
                    Intent intent = new Intent(MainActivity.this, EventView.class);
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
                    startActivityForResult(new Intent(MainActivity.this, ShareView.class), SHARE_EVENT);
                    //share(m_selectedEvent);
                    return true;
                default:
                    m_currentContextView = null;
                    return false;
            }

        }
        return false;
    }

    public void share(Event e, String phoneNum) {
        ShareEvent se = new ShareEvent(phoneNum);
        se.execute(e);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EDIT_EVENT:
                if (resultCode == Activity.RESULT_OK) {
                    m_eventCache.remove(m_selectedEvent);
                    drawEvents(getOrderedEventList());
                }
                break;
            case SHARE_EVENT:
                if (resultCode == Activity.RESULT_OK) {
                    //String phoneNum = data.getStringExtra("phoneNum");
                    //share(m_selectedEvent, phoneNum);
                }
        }
    }
}
