package com.example.eptay.byteMeCalendar;

import java.util.ArrayList;

public class Event {
    public enum RepeatingType { NONE, DAILY, WEEKLY, MONTHLY, YEARLY }

    private String m_name;
    private String m_description;
    private int m_startHour;
    private int m_startMin;
    private int m_endHour;
    private int m_endMin;
    private EventCategory m_category = null;
    private boolean m_repeating = false;
    private Day m_startingDay;
    private Day m_endingDay;
    private RepeatingType m_repeatingType;

    public Event(String name, String description, int startHour, int startMin, int endHour, int endMin, RepeatingType repeatingType, Day startDay, Day endDay, EventCategory category) {
        m_name = name;
        m_description = description;
        m_startHour = startHour;
        m_startMin = startMin;
        m_endHour = endHour;
        m_endMin = endMin;
        m_repeatingType = repeatingType;
        m_startingDay = startDay;
        m_endingDay = endDay;
        m_category = category;
    }
    public RepeatingType getRepeatingType() { return m_repeatingType; }
    public String getName() { return m_name; }
    public String getDescription() { return m_description; }
    public int getStartingHour() { return m_startHour; }
    public int getStartingMin() { return m_startMin; }
    public int getEndingHour() { return m_endHour; }
    public int getEndingMin() { return m_endMin; }
    public EventCategory getCategory() { return m_category; }
    public boolean isRepeating() { return m_repeating; }
    public Day getStartDay() { return m_startingDay; }
    public Day getEndDay() { return m_endingDay; }

    public void setRepeatingType(RepeatingType repeatingType) { m_repeatingType = repeatingType; }
    public void setName(String name) { m_name = name; }
    public void setDescription(String description) { m_description = description; }
    public void setCategory(EventCategory category) { m_category = category; }

    public void setStartingTime(int startingHour, int startingMin) {
        m_startHour = startingHour;
        m_startMin = startingMin;
    }
    public void setEndingTime(int endingHour, int endingMin) {
        m_endHour = endingHour;
        m_endMin = endingMin;
    }
    public void deleteCategory(EventCategory category) {
        ArrayList<Event> events = category.getEvents();
        for (int i = 0; i < events.size(); ++i) {
            events.set(i, null);
        }
    }

    @Override
    public String toString() {
        return m_name + ": \"" + m_description + "\" " + m_startHour + ":" + m_startMin + "-" + m_endHour + ":" + m_endMin;
    }
}
