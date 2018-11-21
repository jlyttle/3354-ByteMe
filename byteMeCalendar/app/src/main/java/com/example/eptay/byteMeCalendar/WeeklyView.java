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
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WeeklyView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Calendar weekInfo = Calendar.getInstance();
        int year = weekInfo.get(Calendar.YEAR);
        int month = weekInfo.get(Calendar.MONTH)+1;
        int day = weekInfo.get(Calendar.DAY_OF_MONTH);
        String week = month+"/"+day+"/"+year;
        int currentDay = Calendar.DAY_OF_WEEK;

        TableLayout table = (TableLayout)findViewById(R.id.AddEvent );
        ListView list = (ListView)findViewById(R.id.WeekList);

        /*
        ArrayList<String> weekNames = new ArrayList<>();
        weekNames.add("Monday");
        weekNames.add("Tuesday");
        weekNames.add("Wednesday");
        weekNames.add("Thursday");
        weekNames.add("Friday");
        weekNames.add("Saturday");
        weekNames.add("Sunday");

        */

/*
        List<Map<String,String>> data = new ArrayList<Map<String,String>>();
        Map<String,String> weekNames = new HashMap<String , String>(2);
        weekNames.put("Monday",week);
        data.add(weekNames);
*/
       // SimpleAdapter adapter = new SimpleAdapter(this, data , android.R.layout.simple_list_item_1,
               // new String[]{"first line" ,"Second line" } , new int[]{R.id.text})



       //ArrayAdapter adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1, weekNames);
       // list.setAdapter(adapter);

        //TextView weekHeader = findViewById(R.id.WeekName);
        //weekHeader.setText(week);

        ArrayList<Day> weekList = new ArrayList<>();
        Day Monday = new Day("Monday" ,"1/1/01" , 4);
        Day Tuesday = new Day("Tuesday" ,"1/2/01" , 8);
        Day Wedensday = new Day("Wedensday" ,"1/3/01" , 1);
        Day Thursday = new Day("Thursday" ,"1/4/01" , 5);
        Day Friday = new Day("Friday" ,"1/5/01" , 9);
        Day Saturday = new Day("Saturday" ,"1/6/01" , 4);
        Day Sunday = new Day("Sunday" ,"1/7/01" , 2);

        weekList.add(Monday);
        weekList.add(Tuesday);
        weekList.add(Wedensday);
        weekList.add(Thursday);
        weekList.add(Friday);
        weekList.add(Saturday);
        weekList.add(Sunday);

        WeekListAdapter adapter = new WeekListAdapter(this, R.layout.weekly_view_layout,weekList);
        list.setAdapter(adapter);

    }

}
