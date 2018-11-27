package com.example.eptay.byteMeCalendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GlobalCalendar {
    private static GregorianCalendar instance = new GregorianCalendar();

    private GlobalCalendar() {
        getInstance();
    }

    public static GregorianCalendar getInstance() {
        if (instance == null) {
            instance = new GregorianCalendar();
        }

        return instance;
    }

    public static int getYear() {
        return instance.get(Calendar.YEAR);
    }

    public static int getMonth() {
        return instance.get(Calendar.MONTH);
    }

    public static int getDayNum() {
        return instance.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour() {
        return instance.get(Calendar.HOUR);
    }

    public static int getMinute() {
        return instance.get(Calendar.MINUTE);
    }

    public static int getDayOfWeek() {
        return instance.get(Calendar.DAY_OF_WEEK);
    }
}
