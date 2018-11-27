package com.example.eptay.byteMeCalendar;

import java.util.ArrayList;

public class Event {
    public enum RepeatingType { NONE, DAILY, WEEKLY, MONTHLY, YEARLY }

    private String m_name;
    private String m_description;
    private int m_startingTime;
    private int m_endingTime;
    private EventCategory m_category;
    private boolean m_repeating = false;
    private Day m_startingDay;
    private Day m_endingDay;
    private RepeatingType m_repeatingType = RepeatingType.NONE;

    public RepeatingType getRepeatingType() { return m_repeatingType; }
    public String getName() { return m_name; }
    public String getDescription() { return m_description; }
    public int getStartingTime() { return m_startingTime; }
    public int getEndingTime() { return m_endingTime; }
    public EventCategory getCategory() { return m_category; }
    public boolean isRepeating() { return m_repeating; }
    public Day getStartDay() { return m_startingDay; }
    public Day getEndDay() { return m_endingDay; }

    public void setRepeatingType(RepeatingType repeatingType) { m_repeatingType = repeatingType; }
    public void setName(String name) { m_name = name; }
    public void setDescription(String description) { m_description = description; }
    public void setCategory(EventCategory category) { m_category = category; }
    public void setStartingTime(int startingTime) { m_startingTime = startingTime; }
    public void setEndingTime(int endingTime) { m_endingTime = endingTime; }
    public void deleteCategory(EventCategory category) {
        ArrayList<Event> events = category.getEvents();
        for (int i = 0; i < events.size(); ++i) {
            events.set(i, null);
        }
    }

    @Override
    public String toString() {
        return m_name + ": \"" + m_description + "\" " + m_startingTime + "-" + m_endingTime;
    }
}
