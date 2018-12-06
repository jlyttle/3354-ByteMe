package com.example.eptay.byteMeCalendar;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class DeleteEventTestJUnit {
    private EventCache eventCache;
    private Day today;
    private Event event;

    @Before
    public void setup() {
        eventCache = EventCache.getInstance();
        today = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
    }

    @Test
    public void testDelete1() {
        event = new Event("Name", "Description", 12, 0, 13, 0, Event.RepeatingType.NONE, today, today, null);
        eventCache.add(event);
        eventCache.remove(event);
        event = eventCache.find(event.getID());
        assertTrue(event == null);
    }

    @Test (expected = NullPointerException.class)
    public void testDelete2(){
        event = null;
        eventCache.remove(event);
    }

}