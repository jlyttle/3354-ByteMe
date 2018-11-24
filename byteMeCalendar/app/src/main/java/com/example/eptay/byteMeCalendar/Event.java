package com.example.eptay.byteMeCalendar;

import java.util.ArrayList;

public class Event {
    private String m_name;
    private String m_description;
    private int m_startingTime;
    private int m_endingTime;
    private EventCategory m_category;
    private boolean m_repeating;

    public String getName() { return m_name; }
    public String getDescription() { return m_description; }
    public int getStartingTime() { return m_startingTime; }
    public int getEndingTime() { return m_endingTime; }
    public EventCategory getCategory() { return m_category; }

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
