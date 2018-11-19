package com.example.eptay.byteMeCalendar;

public class Event {
    String m_description;
    int m_startingTime;
    int m_endingTime;
    EventCategory m_category;
    boolean m_repeating;

    String getDescription() { return m_description; }
    int getStartingTime() { return m_startingTime; }
    int getEndingTime() { return m_endingTime; }
    EventCategory getCategory() { return m_category; }

    void setDescription(String description) { m_description = description; }
    void setCategory(EventCategory category) { m_category = category; }
    void setStartingTime(int startingTime) { m_startingTime = startingTime; }
    void setEndingTime(int endingTime) { m_endingTime = endingTime; }


}
