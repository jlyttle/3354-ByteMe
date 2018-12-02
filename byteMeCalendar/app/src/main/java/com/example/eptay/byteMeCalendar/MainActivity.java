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
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.CalendarView;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    /* MEMBER VARIABLES */
    private DrawerLayout m_drawerLayout;
    private CalendarView m_calendarView;
    private FloatingActionButton m_fab;
    private Calendar m_calendar = new GregorianCalendar();
    private SharedPreferences m_preferences;
    private EventCache m_eventCache;

    int m_month = m_calendar.get(Calendar.MONTH);
    int m_day = m_calendar.get(Calendar.DAY_OF_MONTH);
    int m_year = m_calendar.get(Calendar.YEAR);

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

        //On changing the date, change the text to be new date
        m_calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                m_year = year;
                m_month = month;
                m_day = day;
                setTitle("Date: " + (month + 1) + " / " + day + " / " + year);
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
}
