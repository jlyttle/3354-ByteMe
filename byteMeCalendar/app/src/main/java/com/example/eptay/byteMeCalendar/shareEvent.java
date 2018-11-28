package com.example.eptay.byteMeCalendar;


import android.os.AsyncTask;

import com.goebl.david.Webb;

public class shareEvent extends AsyncTask <Void, Void, String>{
    public shareEvent(){

    }

    @Override
    protected String doInBackground(Void... voids) {
        Webb webb = Webb.create();
        webb.post("http://10.0.2.2:5000/send")
                .param("msg", "hi")
                .param("phoneNum", "+19564550732")
                .ensureSuccess()
                .asVoid();
        return null;
    }




}


