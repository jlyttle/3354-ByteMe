package com.example.eptay.byteMeCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
    Singleton Class used for getting Calendar information
*/

public class GlobalCalendar {

    private static GregorianCalendar instance = new GregorianCalendar();

    /* METHODS */
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

    public static void setNextDay() {
        instance.add(Calendar.DATE, 1);
    }

    public static void setDay(int year, int month, int day) {
        instance.set(year, month, day);
    }

    public static void setPrevDay() {
        instance.add(Calendar.DATE, -1);
    }

    public static void setNextWeek() {
        instance.add(Calendar.DATE, 7);
    }

    public static void setPrevWeek() {
        instance.add(Calendar.DATE, -7);
    }

    /**
     * This method does not take any parameters. This method calculates the days and gets the week
     * @return week
     */
    public static Day[] getWeek() {
        Day[] week = new Day[7];

        int yearTemp = getYear();
        int monthTemp = getMonth();
        int dayTemp = getDayNum();
        int dayOfWeek = getDayOfWeek() - 1;

        for (int i = 0; i < dayOfWeek; ++i) {
            setPrevDay();
        }
        for (int i = 0; i < 7; ++i) {
            int year = getYear();
            int month = getMonth();
            int day = getDayNum();
            week[i] = new Day(year, month, day);
            setNextDay();
        }
        setDay(yearTemp, monthTemp, dayTemp);

        return week;
    }

    /**
     * This method does not take in any parameters. It returns the date in a specific format
     * @return formated date
     */
    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        return dateFormat.format(instance.getTime());
    }

    public static int getHour() {
        return instance.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute() {
        return instance.get(Calendar.MINUTE);
    }

    public static int getDayOfWeek() {
        return instance.get(Calendar.DAY_OF_WEEK);
    }
}
