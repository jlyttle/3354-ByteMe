package com.example.eptay.byteMeCalendar;

import org.junit.Before;
import org.junit.Test;

public class AddEventTestJUnit {
    private EventCache eventCache;
    private Day today;

    @Before
    public void setup() {
        eventCache = EventCache.getInstance();
        today = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
    }

    @Test //Test with valid values
    public void testAddEvent1() {
        Event event = new Event("Name", "Description", 12, 0, 13, 0, Event.RepeatingType.NONE, today, today, null);
        eventCache.add(event);
        eventCache.remove(event);
    }

    @Test (expected = IllegalArgumentException.class) //Test with null values for members of the event
    public void testAddEvent2() {
        Event event = new Event(null, null, 0, 0, 0, 0, Event.RepeatingType.NONE, null, null, null);
        eventCache.add(event);
    }

    @Test (expected = IllegalArgumentException.class) //Test with an invalid hour value
    public void testAddEvent3() {
        Event event = new Event("Name", "Description", 25, 0, 26, 0, Event.RepeatingType.NONE, today, today, null);
        eventCache.add(event);
    }

    @Test (expected = IllegalArgumentException.class) //Test with an ending time that's before the starting time
    public void testAddEvent4() {
        Event event = new Event("Name", "Description", 12, 31, 12, 30, Event.RepeatingType.NONE, today, today, null);
        eventCache.add(event);
    }
}
