package com.example.eptay.byteMeCalendar;

import java.util.ArrayList;
import java.util.UUID;

/*
    Class used to store information of an event within an event object
*/

public class Event implements Comparable {
    /* MEMBER VARIABLES */
    public enum RepeatingType { NONE, DAILY, WEEKLY, MONTHLY, YEARLY }
    public enum CategoryType {NONE, BLUE, ORANGE, GREEN, YELLOW, RED, PURPLE}
    private String m_name;
    private String m_description;
    private int m_startHour;
    private int m_startMin;
    private int m_endHour;
    private int m_endMin;
    private CategoryType m_category;
    private boolean m_repeating = false;
    private Day m_startingDay;
    private Day m_endingDay;
    private RepeatingType m_repeatingType;
    private String m_eventID = UUID.randomUUID().toString();

    /* METHODS */
    public Event(String name, String description, int startHour, int startMin, int endHour, int endMin, RepeatingType repeatingType, Day startDay, Day endDay, CategoryType categoryType) {
        m_name = name;
        m_description = description;
        m_startHour = startHour;
        m_startMin = startMin;
        m_endHour = endHour;
        m_endMin = endMin;
        m_repeatingType = repeatingType;
        if (m_repeatingType != RepeatingType.NONE) {
            m_repeating = true;
        }
        m_startingDay = startDay;
        m_endingDay = endDay;
        m_category = categoryType;
    }
    public RepeatingType getRepeatingType() { return m_repeatingType; }
    public String getName() { return m_name; }
    public String getDescription() { return m_description; }
    public int getStartingHour() { return m_startHour; }
    public int getStartingMin() { return m_startMin; }
    public int getEndingHour() { return m_endHour; }
    public int getEndingMin() { return m_endMin; }
    public CategoryType getCategory() { return m_category; }
    public boolean isRepeating() { return m_repeating; }
    public Day getStartDay() { return m_startingDay; }
    public Day getEndDay() { return m_endingDay; }
    public String getID() { return m_eventID; }
    public void setRepeatingType(RepeatingType repeatingType) { m_repeatingType = repeatingType; }
    public void setName(String name) { m_name = name; }
    public void setDescription(String description) { m_description = description; }
    public void setCategory(CategoryType category) { m_category = category; }

    public void setStartingTime(int startingHour, int startingMin) {
        m_startHour = startingHour;
        m_startMin = startingMin;
    }
    public void setEndingTime(int endingHour, int endingMin) {
        m_endHour = endingHour;
        m_endMin = endingMin;
    }

    @Override
    public String toString() {
        return m_name + ": \"" + m_description + "\" " + m_startHour + ":" + m_startMin + "-" + m_endHour + ":" + m_endMin;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Event) {
            if (((Event) o).m_startHour == this.m_startHour) {
                if (((Event) o).m_startMin > this.m_startMin) {
                    return 1;
                }
                else if (((Event) o).m_startMin < this.m_startMin) {
                    return -1;
                }
                return 0;
            }
            else if (((Event) o).m_startHour > this.m_startHour) {
                return 1;
            }
        }
        return -1;
    }

    public boolean equals(Object o) {
        if (o instanceof Event) {
            if (((Event) o).m_eventID == this.m_eventID) {
                return true;
            }
        }
        return false;
    }
}
