package com.example.eptay.byteMeCalendar;

import android.content.Intent;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;




public class WeeklyView extends AppCompatActivity {
    FloatingActionButton swipe;
    int swipeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        ArrayList<Day> weekList = new ArrayList<>();


        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);

        int dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK))-1;
        int swipeDisplacement = swipeCount*7;
        calendar.add(Calendar.DAY_OF_YEAR, -dayOfWeek);
        calendar.add(Calendar.DAY_OF_YEAR, swipeDisplacement);
        Date dateSunday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1 );
        Date dateMonday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dateTuesday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dateWedensday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dateThursday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dateFriday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date dateSaturday = calendar.getTime();



        String mondayString = dateFormat.format(dateMonday);
        String tuesdayString = dateFormat.format(dateTuesday);
        String wedensdayString = dateFormat.format(dateWedensday);
        String thursdayString = dateFormat.format(dateThursday);
        String fridayString = dateFormat.format(dateFriday);
        String saturdayString = dateFormat.format(dateSaturday);
        String sundayString = dateFormat.format(dateSunday);

        Day Sunday = new Day("Sunday" ,sundayString , 2);
        Day Monday = new Day("Monday" ,mondayString ,3);
        Day Tuesday = new Day("Tuesday" ,tuesdayString , 8);
        Day Wedensday = new Day("Wedensday" ,wedensdayString , 1);
        Day Thursday = new Day("Thursday" ,thursdayString , 5);
        Day Friday = new Day("Friday" ,fridayString , 9);
        Day Saturday = new Day("Saturday" ,saturdayString , 4);

        weekList.add(Sunday);
        weekList.add(Monday);
        weekList.add(Tuesday);
        weekList.add(Wedensday);
        weekList.add(Thursday);
        weekList.add(Friday);
        weekList.add(Saturday);






        TableLayout table = (TableLayout)findViewById(R.id.NextWeek );
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





        WeekListAdapter adapter = new WeekListAdapter(this, R.layout.weekly_view_layout,weekList);
        list.setAdapter(adapter);

        swipe = findViewById(R.id.NextWeek);

        swipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
              swipeCount++;
              System.out.println(swipeCount);

            }
        });

    }

    public void generateDates(){

    }

}
