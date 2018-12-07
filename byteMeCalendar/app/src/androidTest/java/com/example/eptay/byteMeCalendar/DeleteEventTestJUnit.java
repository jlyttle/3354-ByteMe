package com.example.eptay.byteMeCalendar;

import org.junit.Before;
import org.junit.Test;

public class DeleteEventTestJUnit {
    private EventCache eventCache;
    private Day today;
    Event event;

    @Before
    public void setup() {
        eventCache = EventCache.getInstance();
        today = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());


    }

    @Test
    public void testDelete() {
        event = new Event("Name", "Description", 12, 0, 13, 0, Event.RepeatingType.NONE, today, today, null);
        eventCache.add(event);
        event = eventCache.find(event.getID());
        //eventCache.remove(event.getID());
    }

    @Test (expected = NullPointerException.class)
    public void testDelete1(){
        event = eventCache.find(event.getID());
        eventCache.remove(event);
    }

}