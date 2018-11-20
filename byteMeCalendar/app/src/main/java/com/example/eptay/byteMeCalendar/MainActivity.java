package com.example.eptay.byteMeCalendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity {
    /* MEMBER VARIABLES */
    CalendarView calendarView;
    FloatingActionButton fab;
    Button ChangeScreen ;


    int m_month = 0;
    int m_day = 0;
    int m_year = 0;

    /* METHODS */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiate each widget on the layout
        calendarView = findViewById(R.id.calendarView);
        fab = findViewById(R.id.floatingActionButton);
        ChangeScreen = findViewById(R.id.Change);

        //On changing the date, change the text to be new date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                m_year = year;
                m_month = month;
                m_day = day;
                setTitle("Date: " + (month + 1) + " / " + day + " / " + year);
            }
        });

        //TODO: On clicking the action button, should change view to event adding form
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Month = " + (m_month + 1) + " Day = " + m_day + " Year = " + m_year, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ChangeScreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this , WeeklyView.class);
                startActivity(intent);

             }
        });


    }
}
