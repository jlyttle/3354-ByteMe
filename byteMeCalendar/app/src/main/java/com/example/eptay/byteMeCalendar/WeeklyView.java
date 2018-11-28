package com.example.eptay.byteMeCalendar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class WeeklyView extends AppCompatActivity implements OnGestureListener {
    FloatingActionButton swipeRight;
    Button swipeLeft;
    int swipeCount = 0;
    public  static ArrayList<Day> weekList = new ArrayList<>();
    public static String mondayString ;
    public static String tuesdayString;
    public static  String wedensdayString ;
    public static String thursdayString ;
    public static String fridayString ;
    public static String saturdayString ;
    public static String sundayString ;
    GestureDetector detector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");


        final Calendar calendar = Calendar.getInstance();
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

        mondayString = dateFormat.format(dateMonday);
        tuesdayString = dateFormat.format(dateTuesday);
        wedensdayString = dateFormat.format(dateWedensday);
        thursdayString = dateFormat.format(dateThursday);
        fridayString = dateFormat.format(dateFriday);
        saturdayString = dateFormat.format(dateSaturday);
        sundayString = dateFormat.format(dateSunday);



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


        final WeekListAdapter adapter = new WeekListAdapter(this, R.layout.weekly_view_layout,weekList);
        list.setAdapter(adapter);
        
        swipeLeft = findViewById(R.id.SwipeBack);

        swipeRight = findViewById(R.id.NextWeek);

        swipeLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                swipeCount--;
                System.out.println(swipeCount);
                generateDates(calendar,swipeCount,dateFormat);

                weekList.clear();

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

                adapter.notifyDataSetChanged();

            }
        });



        swipeRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
              swipeCount++;
              System.out.println(swipeCount);
              generateDates(calendar,swipeCount,dateFormat);

              weekList.clear();

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

                adapter.notifyDataSetChanged();

            }
        });

    }

    public void generateDates(Calendar calendar , int swipeCount , DateFormat dateFormat ){
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



         mondayString = dateFormat.format(dateMonday);
         tuesdayString = dateFormat.format(dateTuesday);
         wedensdayString = dateFormat.format(dateWedensday);
        thursdayString = dateFormat.format(dateThursday);
         fridayString = dateFormat.format(dateFriday);
         saturdayString = dateFormat.format(dateSaturday);
         sundayString = dateFormat.format(dateSunday);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float Y, float X) {
        return false;
    }

}
