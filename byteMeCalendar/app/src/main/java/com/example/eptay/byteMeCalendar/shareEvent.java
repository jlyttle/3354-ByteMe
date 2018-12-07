package com.example.eptay.byteMeCalendar;

import android.os.AsyncTask;
import com.goebl.david.Webb;

public class ShareEvent extends AsyncTask <Event, Void, String> {
    String m_phoneNum;

    public ShareEvent(String phoneNum) {
        //TODO Check this is a good number
        m_phoneNum = phoneNum;
    }

    @Override
    protected String doInBackground(Event... events) {
        //String title, desc, start, end;
        Webb webb = Webb.create();
        Event e = events[0];
        webb.post("http://10.0.2.2:5000/send")
                .param("title", e.getName())
                .param("desc", e.getDescription())
                .param("startDay", e.getStartDay().toString())
                .param("endDay", e.getEndDay().toString())
                .param("startMin", e.getStartingMin())
                .param("startHour", e.getStartingHour())
                .param("endMin", e.getEndingMin())
                .param("endHour", e.getEndingHour())
                .param("phoneNum", m_phoneNum)
                .ensureSuccess()
                .asVoid();
        return null;
    }

    //create function to call server and append event info
    //update server route
    // give button functionality
    // add intent filter
}