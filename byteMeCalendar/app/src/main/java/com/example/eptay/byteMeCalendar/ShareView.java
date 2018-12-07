package com.example.eptay.byteMeCalendar;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*

*/

public class ShareView extends AppCompatActivity {
    private Button button;
    private TextView numField;
    private String m_phoneNum;

    /* METHODS */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button = findViewById(R.id.phoneSelect);
        numField = findViewById(R.id.phoneNumField);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //TODO Check that this is a valid phone number
                m_phoneNum = numField.getText().toString();
                SmsManager sms = SmsManager.getDefault();
                PendingIntent sentPI;
                String SENT = "SMS_SENT";

                sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(SENT), 0);

                String[] permissions = {Manifest.permission.SEND_SMS};

                if (ContextCompat.checkSelfPermission(ShareView.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    // Ask for permission
                    ActivityCompat.requestPermissions(ShareView.this, permissions, 1);
                }
                else {
                    // Permission has already been granted
                    sms.sendTextMessage(m_phoneNum, null, "Test message", sentPI, null);
                }
                Intent intent = new Intent();
                intent.putExtra("phoneNum", m_phoneNum);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

}
