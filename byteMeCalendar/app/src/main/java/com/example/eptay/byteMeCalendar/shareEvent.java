package com.example.eptay.byteMeCalendar;


import android.os.AsyncTask;

import com.goebl.david.Webb;

public class shareEvent extends AsyncTask <String, Void, String>{
    public shareEvent(){

    }

    @Override
    protected String doInBackground(String... url) {
        Webb webb = Webb.create();
        webb.post("http://10.0.2.2:5000/send")
                .param("msg", url[0])
                .param("phoneNum", url[1])
                .ensureSuccess()
                .asVoid();
        return null;
    }




}


