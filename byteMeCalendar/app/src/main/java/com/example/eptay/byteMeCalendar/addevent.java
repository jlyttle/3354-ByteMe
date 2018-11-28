package com.example.eptay.byteMeCalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class addevent extends AppCompatActivity {

    private Button select;
    private int m_name;
    private int m_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addevent);
    }
}
