package com.example.eptay.byteMeCalendar;

import java.util.ArrayList;

class EventCategory {
    private String name;
    private String color;
    private ArrayList<Event> events;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public void addEvent(Event event) { events.add(event); }
    public ArrayList<Event> getEvents() { return events; }
}
