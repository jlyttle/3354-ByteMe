package com.example.eptay.byteMeCalendar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class EventView extends AppCompatActivity {
    private Spinner repeatMode;
    private Button startingTimeViewButton;
    private Button endingTimeViewButton;
    private final int DATE_START_SELECTOR = 0;
    private final int TIME_START_SELECTOR = 1;
    private final int DATE_END_SELECTOR = 2;
    private final int TIME_END_SELECTOR = 3;

    private int startYear = GlobalCalendar.getYear();
    private int startMonth = GlobalCalendar.getMonth();
    private int startDay = GlobalCalendar.getDayNum();
    private int endYear;
    private int endMonth;
    private int endDay;

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        startingTimeViewButton = findViewById(R.id.startingTimeViewButton);
        endingTimeViewButton = findViewById(R.id.endingTimeViewButton);
        repeatMode = findViewById(R.id.spinner);
        String[] modes = {"None", "Daily", "Weekly", "Monthly"};
        repeatMode.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, modes));

        startingTimeViewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(EventView.this, TimeSelector.class), TIME_START_SELECTOR);
            }
        });
        endingTimeViewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(EventView.this, TimeSelector.class), TIME_END_SELECTOR);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (DATE_START_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    //Extract the data returned from the child Activity.
                    startYear = data.getIntExtra("year", GlobalCalendar.getYear());
                    startMonth = data.getIntExtra("month", GlobalCalendar.getMonth());
                    startDay = data.getIntExtra("day", GlobalCalendar.getDayNum());
                }
                break;
            case (TIME_START_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    startHour = data.getIntExtra("hour", GlobalCalendar.getHour());
                    startMinute = data.getIntExtra("minute", GlobalCalendar.getMinute());
                }
                break;
            case (DATE_END_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    //TODO Make the default end date an hour after the start date
                    endYear = data.getIntExtra("year", GlobalCalendar.getYear());
                    endMonth = data.getIntExtra("month", GlobalCalendar.getMonth());
                    endDay = data.getIntExtra("day", GlobalCalendar.getDayNum());
                }
                break;
            case (TIME_END_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    //TODO Make the default end time an hour after the start time
                    endHour = data.getIntExtra("hour", GlobalCalendar.getHour());
                    endMinute = data.getIntExtra("minute", GlobalCalendar.getMinute());
                }
                break;
        }
    }
}
