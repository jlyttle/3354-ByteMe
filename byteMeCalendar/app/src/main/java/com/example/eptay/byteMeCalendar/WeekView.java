package com.example.eptay.byteMeCalendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class WeekView extends AppCompatActivity {

    public static ArrayList<Day> weekList = new ArrayList<>();
    private ConstraintLayout m_constraintLayout;
    private Day[] m_week;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        weekList.clear();
        final WeekListAdapter adapter = new WeekListAdapter(this, R.layout.week_view_layout, weekList);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        m_week = GlobalCalendar.getWeek();

        for (int i = 0; i < m_week.length; ++i) {
            weekList.add(m_week[i]);
        }


        ListView list = findViewById(R.id.WeekList);
        list.setAdapter(adapter);

        m_constraintLayout = findViewById(R.id.weekLayout);

        m_constraintLayout.setOnTouchListener(new OnSwipeTouchListener(WeekView.this) {
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
                startActivity(new Intent(WeekView.this, DayView.class));
            }
        });






    }
}
