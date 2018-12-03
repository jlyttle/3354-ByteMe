package com.example.eptay.byteMeCalendar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class EventView extends AppCompatActivity {
    private TextView startTimeText;
    private TextView endTimeText;
    private TextView title;
    private TextView description;
    private EventCategory category;
    private Spinner repeatMode;
    private Spinner selectCategory;
    private Button startingTimeViewButton;
    private Button endingTimeViewButton;
    private Button addEvent;
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

        String[] modes = {"None", "Daily", "Weekly", "Monthly"};
        repeatMode.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, modes));
        final List<EventCategory> categories = EventCache.getInstance().getCategories();
        String[] categoriesStr = new String[categories.size()];
        categoriesStr = categories.toArray(categoriesStr);
        selectCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoriesStr));

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
        repeatMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        repeatType = Event.RepeatingType.NONE;
                        break;
                    case 1:
                        repeatType = Event.RepeatingType.DAILY;
                        break;
                    case 2:
                        repeatType = Event.RepeatingType.WEEKLY;
                        break;
                    case 3:
                        repeatType = Event.RepeatingType.MONTHLY;
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        selectCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                category = categories.get(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleText = title.getText().toString();
                String descriptionText = description.getText().toString();
                Event event = new Event(titleText, descriptionText, startHour, startMinute, endHour, endMinute, repeatType, startDay, endDay, category);
                EventCache.getInstance().add(event);
                setResult(Activity.RESULT_OK, new Intent());
                finish();
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
                    startDayNum = data.getIntExtra("day", GlobalCalendar.getDayNum());
                }
                break;
            case (TIME_START_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    startHour = data.getIntExtra("hour", GlobalCalendar.getHour());
                    startMinute = data.getIntExtra("minute", GlobalCalendar.getMinute());
                    String startTime = convertTime(startHour,startMinute);
                    TextView startTimeText = (TextView) findViewById(R.id.startingTimeID);
                    startTimeText.setText(startTime);
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

                }
                break;
            case (TIME_END_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    //TODO Make the default end time an hour after the start time
                    //TODO Check that the user entered a time after or on the start time
                    endHour = data.getIntExtra("hour", GlobalCalendar.getHour());
                    endMinute = data.getIntExtra("minute", GlobalCalendar.getMinute());
                    String endTime = convertTime(endHour,endMinute);
                    endTimeText = findViewById(R.id.endingTimeID);
                    endTimeText.setText(endTime);
                }
                break;
        }
    }

    public String convertTime(int hour , int minute){
        String min = Integer.toString(minute);
        String amPm;
        if(hour > 12 ){
            hour = hour - 12;
            amPm = " PM";
        }else{
            amPm = " AM";
        }
        if(min.length()==1){
            min = "0"+min;
        }
        return hour+":"+min+amPm;

    }

}
