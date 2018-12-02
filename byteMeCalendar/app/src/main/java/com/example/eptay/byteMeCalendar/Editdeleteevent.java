package com.example.eptay.byteMeCalendar;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Editdeleteevent extends AppCompatActivity {

    private Button select;
    private int m_name;
    private int m_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdeleteevent);

        select.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", m_name);
                intent.putExtra("color", m_color);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
