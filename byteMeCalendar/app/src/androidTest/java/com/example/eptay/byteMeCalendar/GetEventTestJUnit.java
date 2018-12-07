package com.example.eptay.byteMeCalendar;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class GetEventTestJUnit {
private EventCache eventCache;
private Day testDay;

    @Before
    public void setup() {
        eventCache = EventCache.getInstance();
        testDay = new Day(GlobalCalendar.getYear(), GlobalCalendar.getMonth(), GlobalCalendar.getDayNum());
    }
    @Test
    //test with valid event
    public void get1() {
        Event event = new Event("Name", "Description", 12, 0, 13, 0, Event.RepeatingType.NONE, testDay, testDay, null);
        eventCache.add(event);
        List<Event> events = eventCache.get(testDay);
        Event event0 = events.get(0);
        assertTrue(event.getID() == event0.getID());
    }

    //test with null values
    @Test (expected = IllegalArgumentException.class)
    public void get2() {
        Event event = new Event(null, null, 0, 0, 0, 0, Event.RepeatingType.NONE, null, null, null);
        eventCache.add(event);
        List<Event> events = eventCache.get(testDay);
        Event event1 = events.get(1);
        assertTrue(event.getID() == event1.getID()) ;
    }

    //remove event, then test .get
    @Test
    public void get3() {
        Event event = new Event("Name", "Description", 12, 0, 13, 0, Event.RepeatingType.NONE, testDay, testDay, null);
        eventCache.add(event);
        eventCache.remove(event);
        List<Event> events = eventCache.get(testDay);
        Event event2 = null;
        for(Event e: events) {
            if(e.getID()==event.getID()){
                event2 = e;
            }
        }
        if(event2 != null){
            assertTrue(event.getID() == event2.getID());
        }

    }

    //test with ending time that is before starting time
    @Test (expected = IllegalArgumentException.class)
    public void get4() {
        Event event = new Event("Name", "Description", 12, 31, 12, 30, Event.RepeatingType.NONE, testDay, testDay, null);
        eventCache.add(event);
        List<Event> events = eventCache.get(testDay);
        Event event3 = events.get(3);
        assertTrue(event.getID() == event3.getID()) ;
    }


}