package com.example.eptay.byteMeCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EventView extends AppCompatActivity {
    /* MEMBER VARIABLES */
    private TextView m_startTimeText;
    private TextView m_endTimeText;
    private TextView title;
    private TextView description;
    private Spinner repeatMode;
    private Spinner selectCategory;
    private Button startingTimeViewButton;
    private Button endingTimeViewButton;
    private Button addEvent;
    private Button m_selectDay;
    private TextView m_endDayText;
    private TextView m_endDayField;
    private final int DATE_START_SELECTOR = 0;
    private final int TIME_START_SELECTOR = 1;
    private final int DATE_END_SELECTOR = 2;
    private final int TIME_END_SELECTOR = 3;
    private int startYear = GlobalCalendar.getYear();
    private int startMonth = GlobalCalendar.getMonth();
    private int startDayNum = GlobalCalendar.getDayNum();
    private int endYear = GlobalCalendar.getYear();
    private int endMonth = GlobalCalendar.getMonth();
    private int endDayNum = GlobalCalendar.getDayNum();
    private Day startDay = new Day(startYear, startMonth, startDayNum);
    private Day endDay = new Day(endYear, endMonth, endDayNum);
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private Event.RepeatingType repeatType;
    private Event.CategoryType categoryType;

    /* METHODS */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);
        title = findViewById(R.id.addTitleField);
        description = findViewById(R.id.addDescriptionField);
        startingTimeViewButton = findViewById(R.id.startingTimeViewButton);
        endingTimeViewButton = findViewById(R.id.endingTimeViewButton);
        repeatMode = findViewById(R.id.spinner);
        selectCategory = findViewById(R.id.spinner2);
        addEvent = findViewById(R.id.accept);
        m_startTimeText = findViewById(R.id.startingTimeID);
        m_endTimeText = findViewById(R.id.endingTimeID);
        m_selectDay = findViewById(R.id.endDaySelect);
        m_endDayText = findViewById(R.id.endDay);
        m_endDayField = findViewById(R.id.endDayField);

        m_endDayField.setText(endDay.toString());
        title.setText(getIntent().getStringExtra("title"));
        description.setText(getIntent().getStringExtra("description"));
        if (getIntent().hasExtra("startHour")) {
            startHour = getIntent().getIntExtra("startHour", 0);
        }
        else {
            startHour = GlobalCalendar.getHour();
        }
        if (getIntent().hasExtra("startMin")) {
            startMinute = getIntent().getIntExtra("startMin", 0);
        }
        else {
            startMinute = GlobalCalendar.getMinute();
        }
        if (getIntent().hasExtra("endHour")) {
            endHour = getIntent().getIntExtra("endHour", 0);
        }
        else {
            endHour = startHour == 23 ? 0 : startHour + 1;
        }
        if (getIntent().hasExtra("endMinute")) {
            endMinute = getIntent().getIntExtra("endMin", 0);
        }
        else {
            endMinute = startMinute;
        }
        String startTime = convertTime(startHour, startMinute);
        m_startTimeText.setText(startTime);
        String endTime = convertTime(endHour, endMinute);
        m_endTimeText.setText(endTime);

        String[] modes = {"None", "Daily", "Weekly", "Monthly"};
        String[] category = {"None", "Blue", "Orange", "Green", "Yellow", "Red", "Purple"};
        repeatMode.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, modes));
        selectCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, category));

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
        m_selectDay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivityForResult(new Intent(EventView.this, DateSelector.class), DATE_END_SELECTOR);
            }
        });
        repeatMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            /**
             * This method takes in parent, view, pos, and id and determines if repeating daily, weekly, or monthly
             * @param parent
             * @param view
             * @param pos
             * @param id
             * @return void
             */
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        repeatType = Event.RepeatingType.NONE;
                        m_endDayField.setVisibility(View.INVISIBLE);
                        m_endDayText.setVisibility(View.INVISIBLE);
                        m_selectDay.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        repeatType = Event.RepeatingType.DAILY;
                        m_endDayField.setVisibility(View.VISIBLE);
                        m_endDayText.setVisibility(View.VISIBLE);
                        m_selectDay.setVisibility(View.VISIBLE);
                        m_selectDay.setClickable(true);
                        break;
                    case 2:
                        repeatType = Event.RepeatingType.WEEKLY;
                        m_endDayField.setVisibility(View.VISIBLE);
                        m_endDayText.setVisibility(View.VISIBLE);
                        m_selectDay.setVisibility(View.VISIBLE);
                        m_selectDay.setClickable(true);
                        break;
                    case 3:
                        repeatType = Event.RepeatingType.MONTHLY;
                        m_endDayField.setVisibility(View.VISIBLE);
                        m_endDayText.setVisibility(View.VISIBLE);
                        m_selectDay.setVisibility(View.VISIBLE);
                        m_selectDay.setClickable(true);
                        break;
                }
            }
            //nothing selected
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        selectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            /**
             * This method takes in parent, view, pos, and id and determines which event color/category
             * @param event
             * @return factor * HOUR_HEIGHT
             */
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        categoryType = Event.CategoryType.NONE;
                        break;
                    case 1:
                        categoryType = Event.CategoryType.BLUE;
                        break;
                    case 2:
                        categoryType = Event.CategoryType.ORANGE;
                        break;
                    case 3:
                        categoryType = Event.CategoryType.GREEN;
                        break;
                    case 4:
                        categoryType = Event.CategoryType.YELLOW;
                        break;
                    case 5:
                        categoryType = Event.CategoryType.RED;
                        break;
                    case 6:
                        categoryType = Event.CategoryType.PURPLE;
                        break;
                }
            }
            //nothing selected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleText = title.getText().toString();
                String descriptionText = description.getText().toString();
                Event event = new Event(titleText, descriptionText, startHour, startMinute, endHour, endMinute, repeatType, startDay, endDay, categoryType);
                EventCache.getInstance().add(event);
                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        });
    }

    /**
     * This method takes in requedtCode, resultCode, and data, and gets specific date and time
     * @param requestCode
     * @param resultCode
     * @param data
     * @return void
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (DATE_START_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    //Extract the data returned from the child Activity.
                    startYear = data.getIntExtra("year", GlobalCalendar.getYear());
                    startMonth = data.getIntExtra("month", GlobalCalendar.getMonth());
                    startDayNum = data.getIntExtra("day", GlobalCalendar.getDayNum());
                }
                break;
            case (TIME_START_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    startHour = data.getIntExtra("hour", GlobalCalendar.getHour());
                    startMinute = data.getIntExtra("minute", GlobalCalendar.getMinute());
                    String startTime = convertTime(startHour,startMinute);
                    m_startTimeText.setText(startTime);
                    System.out.println(startMinute);
                }
                break;
            case (DATE_END_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    //TODO Make the default end date an hour after the start date
                    //TODO Check that the user entered a date after or on the start date
                    endYear = data.getIntExtra("year", GlobalCalendar.getYear());
                    endMonth = data.getIntExtra("month", GlobalCalendar.getMonth());
                    endDayNum = data.getIntExtra("day", GlobalCalendar.getDayNum());
                    endDay = new Day(endYear, endMonth, endDayNum);

                    m_endDayField.setText(endDay.toString());
                }
                break;
            case (TIME_END_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    endHour = data.getIntExtra("hour", GlobalCalendar.getHour());
                    endMinute = data.getIntExtra("minute", GlobalCalendar.getMinute());
                    if (endHour < startHour || (endHour == startHour && endMinute < startMinute)) {
                        Toast.makeText(EventView.this, "Cannot enter an end time before a start time", Toast.LENGTH_SHORT).show();
                        endHour = startHour;
                        endMinute = startMinute;
                    }
                    String endTime = convertTime(endHour,endMinute);
                    m_endTimeText.setText(endTime);
                }
                break;
        }
    }

    /**
     * This method takes in hour and minute and determine AM or PM
     * @param hour
     * @param minute
     * @return hour + min + am or pm
     */
    public String convertTime(int hour , int minute){
        String min = Integer.toString(minute);
        String amPm;
        if (hour == 0) {
            hour = 12;
        }

        if (hour >= 12) {
            if (hour > 12) {
                hour = hour - 12;
            }
            amPm = " PM";
        }
        else {
            amPm = " AM";
        }
        if(min.length() == 1){
            min = "0" + min;
        }
        return hour+":"+min+amPm;

    }
}
