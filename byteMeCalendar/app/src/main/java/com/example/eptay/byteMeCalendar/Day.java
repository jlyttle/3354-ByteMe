package com.example.eptay.byteMeCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Day {
    public Day(int year, int month, int day) {
        m_year = year;
        m_month = month;
        m_day = day;
        //Calendar calendar = new GregorianCalendar(year, month, day);
        m_dayOfWeek = GlobalCalendar.getDayOfWeek();
        m_dayName = m_weekdays[m_dayOfWeek - 1];
    }

    String[] m_weekdays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private ArrayList<Event> m_events = new ArrayList<>();
    private int m_year;
    private int m_month;
    private int m_day;
    private String m_dayName;
    private int m_dayOfWeek;

    public int getYear() { return m_year; }
    public int getMonth() { return m_month; }
    public int getDayNum() { return m_day; }
    public String getDayName() { return m_dayName; }
    public int getDayOfWeek() { return m_dayOfWeek; }
    public ArrayList<Event> getEvents() { return m_events; }

    public void addEvent(Event event) { m_events.add(event); }
    public void removeEvent(Event event) throws Exception {
        if (m_events.contains(event))
        {
            m_events.remove(event);
        }
        else
        {
            //Could not find the event to remove
            throw new Exception("Could not find event to remove: " + event);
        }
    }
}
