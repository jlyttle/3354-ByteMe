package com.example.eptay.byteMeCalendar;

import android.os.AsyncTask;

import com.goebl.david.Webb;

public class shareEvent extends AsyncTask <Event, Void, String>{
    public shareEvent(){

    }

    @Override
    protected String doInBackground(Event... events) {

        String title, desc, start, end;
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
                .ensureSuccess()
                .asVoid();
        return null;
    }

    //create function to call server and append event info
    //update server route
    // give button functionality
    // add intent filter


}