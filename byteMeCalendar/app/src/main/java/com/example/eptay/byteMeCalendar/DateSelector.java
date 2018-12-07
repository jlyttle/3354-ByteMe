package com.example.eptay.byteMeCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

/*
    Class Date
*/

public class DateSelector extends AppCompatActivity {
    private DatePicker datePicker1;
    private Button select;
    private int m_year;
    private int m_month;
    private int m_day;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_selector);
        select = findViewById(R.id.selectDate);
        datePicker1 = findViewById(R.id.datePicker1);
        datePicker1.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        m_year = datePicker1.getYear();
        m_month = datePicker1.getMonth();
        m_day = datePicker1.getDayOfMonth();

        select.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("year", m_year);
                intent.putExtra("month", m_month);
                intent.putExtra("day", m_day);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        datePicker1.setOnDateChangedListener(
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        m_year = year;
                        m_month = month;
                        m_day = day;
                    }
                });
    }
}

