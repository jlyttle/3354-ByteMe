package com.example.eptay.byteMeCalendar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class WeeklyView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Calendar weekInfo = Calendar.getInstance();
        int year = weekInfo.get(Calendar.YEAR);
        int month = weekInfo.get(Calendar.MONTH);
        int day = weekInfo.get(Calendar.DAY_OF_MONTH);
        String week = month+"/"+day+"/"+year;
        int currentDay = Calendar.DAY_OF_WEEK;

        TableLayout table = (TableLayout)findViewById(R.id.AddEvent );
        ListView list = (ListView)findViewById(R.id.WeekList);

        ArrayList<String> weekNames = new ArrayList<>();
        weekNames.add("Monday");
        weekNames.add("Tuesday");
        weekNames.add("Wednesday");
        weekNames.add("Thursday");
        weekNames.add("Friday");
        weekNames.add("Saturday");
        weekNames.add("Sunday");

        ArrayAdapter adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1, weekNames);
        list.setAdapter(adapter);

        TextView weekHeader = findViewById(R.id.WeekName);
        weekHeader.setText(week);

    }

}
