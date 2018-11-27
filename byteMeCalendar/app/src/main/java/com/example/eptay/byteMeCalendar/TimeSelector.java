package com.example.eptay.byteMeCalendar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class TimeSelector extends AppCompatActivity {
    private TimePicker timePicker1;
    private Button select;
    private int m_hour;
    private int m_min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_selector);
        select = findViewById(R.id.selectTime);
        timePicker1 = findViewById(R.id.timePicker1);
        timePicker1.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        m_hour = timePicker1.getCurrentHour();
        m_min = timePicker1.getCurrentMinute();

        select.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("hour", m_hour);
                intent.putExtra("minute", m_min);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        timePicker1.setOnTimeChangedListener(
                new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        m_hour = hourOfDay;
                        m_min = minute;
                    }
                });
    }
}
