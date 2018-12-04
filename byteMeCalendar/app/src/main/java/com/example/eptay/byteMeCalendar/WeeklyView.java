package com.example.eptay.byteMeCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class WeeklyView extends AppCompatActivity {
    public static final int SWIPE_THRESHOLD = 100;
    public static final int VEOLOCITY_THRESHOLD = 100;
    FloatingActionButton swipeRight;
    int swipeCount = 0;
    //SwipeDetector swipeDetector = new SwipeDetector(swipeCount);
    Button swipeLeft;
    public static ArrayList<Day> weekList = new ArrayList<>();
    private ConstraintLayout m_constraintLayout;
    private Day[] m_week;

    public static String mondayString ;
    public static String tuesdayString;
    public static String wednesdayString;
    public static String thursdayString ;
    public static String fridayString ;
    public static String saturdayString ;
    public static String sundayString ;
    GestureDetector detector;
    final DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        weekList.clear();
        final WeekListAdapter adapter = new WeekListAdapter(this, R.layout.weekly_view_layout,weekList);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_week = GlobalCalendar.getWeek();

        for (int i = 0; i < m_week.length; ++i) {
            weekList.add(m_week[i]);
        }
        /*Date now = new Date();
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
        wednesdayString = dateFormat.format(dateWedensday);
        thursdayString = dateFormat.format(dateThursday);
        fridayString = dateFormat.format(dateFriday);
        saturdayString = dateFormat.format(dateSaturday);
        sundayString = dateFormat.format(dateSunday);

        m_week[0] = new Day("Sunday" ,sundayString , 2);
        Day Monday = new Day("Monday" ,mondayString ,3);
        Day Tuesday = new Day("Tuesday" ,tuesdayString , 8);
        Day Wedensday = new Day("Wednesday" , wednesdayString, 1);
        Day Thursday = new Day("Thursday" ,thursdayString , 5);
        Day Friday = new Day("Friday" ,fridayString , 9);
        Day Saturday = new Day("Saturday" ,saturdayString , 4);

        weekList.add(Sunday);
        weekList.add(Monday);
        weekList.add(Tuesday);
        weekList.add(Wedensday);
        weekList.add(Thursday);
        weekList.add(Friday);
        weekList.add(Saturday);*/

        //TableLayout table = (TableLayout)findViewById(R.id.NextWeek );
        ListView list = (ListView)findViewById(R.id.WeekList);
        //list.setOnTouchListener(swipeDetector);
        list.setAdapter(adapter);
        
        //swipeLeft = findViewById(R.id.SwipeBack);
        //swipeRight = findViewById(R.id.NextWeek);
        m_constraintLayout = findViewById(R.id.weekLayout);

        m_constraintLayout.setOnTouchListener(new OnSwipeTouchListener(WeeklyView.this) {
            public void onSwipeRight() {
                //Go to previous week
                GlobalCalendar.setPrevWeek();
                weekList.clear();
                m_week = GlobalCalendar.getWeek();

                for (int i = 0; i < m_week.length; ++i) {
                    weekList.add(m_week[i]);
                }
                adapter.notifyDataSetChanged();
            }
            public void onSwipeLeft() {
                //Go to next week
                GlobalCalendar.setNextWeek();
                weekList.clear();
                m_week = GlobalCalendar.getWeek();

                for (int i = 0; i < m_week.length; ++i) {
                    weekList.add(m_week[i]);
                }
                adapter.notifyDataSetChanged();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Day currentDay = m_week[position];
                GlobalCalendar.setDay(currentDay.getYear(), currentDay.getMonth(), currentDay.getDayNum());
                startActivity(new Intent(WeeklyView.this, scrollingdayview.class));
            }
        });

        /*swipeLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                swipeCount--;
                generateDates(calendar,swipeCount,dateFormat);
                weekList.clear();
                Day Sunday    =  new Day("Sunday" ,sundayString , 2);
                Day Monday    =  new Day("Monday" ,mondayString ,3);
                Day Tuesday   =  new Day("Tuesday" ,tuesdayString , 8);
                Day Wednesday =  new Day("Wednesday" , wednesdayString, 1);
                Day Thursday  =  new Day("Thursday" ,thursdayString , 5);
                Day Friday    =  new Day("Friday" ,fridayString , 9);
                Day Saturday  =  new Day("Saturday" ,saturdayString , 4);

                weekList.add(Sunday);
                weekList.add(Monday);
                weekList.add(Tuesday);
                weekList.add(Wednesday);
                weekList.add(Thursday);
                weekList.add(Friday);
                weekList.add(Saturday);

                TableLayout table = (TableLayout)findViewById(R.id.NextWeek );
                ListView list = (ListView)findViewById(R.id.WeekList);
                adapter.notifyDataSetChanged();
            }
        });*/

        /*swipeRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                swipeCount++;
                generateDates(calendar,swipeCount,dateFormat);
                weekList.clear();
                Day Sunday    =  new Day("Sunday" ,sundayString , 2);
                Day Monday    =  new Day("Monday" ,mondayString ,3);
                Day Tuesday   =  new Day("Tuesday" ,tuesdayString , 8);
                Day Wednesday =  new Day("Wednesday" , wednesdayString, 1);
                Day Thursday  =  new Day("Thursday" ,thursdayString , 5);
                Day Friday    =  new Day("Friday" ,fridayString , 9);
                Day Saturday  =  new Day("Saturday" ,saturdayString , 4);

                weekList.add(Sunday);
                weekList.add(Monday);
                weekList.add(Tuesday);
                weekList.add(Wednesday);
                weekList.add(Thursday);
                weekList.add(Friday);
                weekList.add(Saturday);

                TableLayout table = (TableLayout)findViewById(R.id.NextWeek );
                ListView list = (ListView)findViewById(R.id.WeekList);
                adapter.notifyDataSetChanged();

            }
        });*/

    }

    /*public void generateDates(Calendar calendar , int swipeCount , DateFormat dateFormat ){
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
         wednesdayString = dateFormat.format(dateWedensday);
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
    public boolean onFling(MotionEvent downEvent, MotionEvent motionEvent, float Y, float X) {
        boolean result = false;

        float diffY = motionEvent.getY() - downEvent.getY();
        float diffX = motionEvent.getX() - downEvent.getX();

        if(Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(diffY) > VEOLOCITY_THRESHOLD) {
            if (diffX > 0) {
               // onSwipeRight( );
            } else {
                onSwipeLeft();
            }
        }
        return result;
    }

    private void onSwipeLeft() {
    }

    private void onSwipeRight() {
        swipeCount++;
        System.out.println("SwipeRight");
        generateDates(calendar,swipeCount,dateFormat);
        weekList.clear();
        Day Sunday    =  new Day("Sunday" ,sundayString , 2);
        Day Monday    =  new Day("Monday" ,mondayString ,3);
        Day Tuesday   =  new Day("Tuesday" ,tuesdayString , 8);
        Day Wedensday =  new Day("Wedensday" , wednesdayString, 1);
        Day Thursday  =  new Day("Thursday" ,thursdayString , 5);
        Day Friday    =  new Day("Friday" ,fridayString , 9);
        Day Saturday  =  new Day("Saturday" ,saturdayString , 4);

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

        ((BaseAdapter)adapter).notifyDataSetChanged();
    }
*/
}
