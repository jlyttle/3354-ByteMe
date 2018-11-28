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

import java.util.List;

public class EventView extends AppCompatActivity {
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
        repeatMode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                switch (position) {
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
        });
        selectCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                category = categories.get(position);
            }
        });
        addEvent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String titleText = title.getText().toString();
                String descriptionText = description.getText().toString();
                Event event = new Event(titleText, descriptionText, startHour, startMinute, endHour, endMinute, repeatType, startDay, endDay, );
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
                }
                break;
            case (DATE_END_SELECTOR):
                if (resultCode == Activity.RESULT_OK) {
                    //TODO Make the default end date an hour after the start date
                    endYear = data.getIntExtra("year", GlobalCalendar.getYear());
                    endMonth = data.getIntExtra("month", GlobalCalendar.getMonth());
                    endDayNum = data.getIntExtra("day", GlobalCalendar.getDayNum());
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
