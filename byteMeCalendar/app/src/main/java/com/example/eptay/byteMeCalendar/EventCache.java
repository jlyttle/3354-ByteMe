package com.example.eptay.byteMeCalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/*
    Singleton class store event objects
*/

public class EventCache {
    /* MEMBER VARIABLES */
    private static EventCache instance;
    private List<Event> m_nonRepeatingEvents = new ArrayList<>();
    private HashMap<Integer, List<Event>> m_repeatingEvents = new HashMap();
    private List<Event.CategoryType> m_eventCategories = new ArrayList<>();

    /* METHODS */
    static {
        instance = new EventCache();
    }

    public static EventCache getInstance() {
        return instance;
    }

    public void add(Event event) throws IllegalArgumentException {
        if (event == null) {
            throw new IllegalArgumentException("Cannot add a null event object.");
        }

        boolean nameIsNull = event.getName() == null;
        boolean descriptionIsNull = event.getDescription() == null;
        boolean startDayIsNull = event.getStartDay() == null;
        boolean endDayIsNull = event.getEndDay() == null;

        if (nameIsNull || descriptionIsNull || startDayIsNull || endDayIsNull) {
            throw new IllegalArgumentException("Cannot add a null event object.");
        }
        else if (event.getStartingHour() > 23 || event.getEndingHour() > 23) {
            throw new IllegalArgumentException("Cannot have an hour greater than 23.");
        }
        else if (event.getStartingHour() > event.getEndingHour() || (event.getStartingHour() == event.getEndingHour() && event.getStartingMin() > event.getEndingMin())) {
            throw new IllegalArgumentException("Cannot have a starting time after an ending time.");
        }

        if (event.isRepeating()) {
            Integer dayOfWeek = event.getStartDay().getDayOfWeek();
            List<Event> eventsForDay = m_repeatingEvents.get(dayOfWeek);
            if (eventsForDay == null) {
                m_repeatingEvents.put(dayOfWeek, new ArrayList<Event>());
                eventsForDay = m_repeatingEvents.get(dayOfWeek);
            }

            eventsForDay.add(event);
            if (event.getRepeatingType() == Event.RepeatingType.DAILY || event.getRepeatingType() == Event.RepeatingType.MONTHLY) {
                for (int i = 1; i < 8; ++i) {
                    m_repeatingEvents.put(i, eventsForDay);
                }
            }
            else {
                m_repeatingEvents.put(dayOfWeek, eventsForDay);
            }
        }
        else {
            m_nonRepeatingEvents.add(event);
        }

        if (event.getCategory() != null) {
            m_eventCategories.add(event.getCategory());
        }
    }

    public List<Event> get(Day day) {
        List<Event> events = new ArrayList<>();

        for (int i = 0; i < m_nonRepeatingEvents.size(); ++i) {
            Event curEvent = m_nonRepeatingEvents.get(i);
            Day curDay = curEvent.getStartDay();
            boolean sameYear = curDay.getYear() == day.getYear();
            boolean sameMonth = curDay.getMonth() == day.getMonth();
            boolean sameDay = curDay.getDayNum() == day.getDayNum();

            if (sameDay && sameMonth && sameYear) {
                events.add(curEvent);
            }
        }

        int dayOfWeek = day.getDayOfWeek();
        List<Event> repeatingEvents = m_repeatingEvents.get(dayOfWeek);

        if (repeatingEvents == null) {
            m_repeatingEvents.put(dayOfWeek, new ArrayList<Event>());
            repeatingEvents = m_repeatingEvents.get(dayOfWeek);
        }

        for (int i = 0; i < repeatingEvents.size(); ++i) {
            Event curEvent = repeatingEvents.get(i);
            Day startDay = curEvent.getStartDay();
            int startYear = startDay.getYear();
            int startMonth = startDay.getMonth();
            int startDayNum = startDay.getDayNum();
            Day endDay = curEvent.getEndDay();
            int endYear = endDay.getYear();
            int endMonth = endDay.getMonth();
            int endDayNum = endDay.getDayNum();
            int year = day.getYear();
            int month = day.getMonth();
            int dayNum = day.getDayNum();
            boolean inYearRange = (startYear <= year) && (year <= endYear);
            boolean inMonthRange = true;
            if (endYear == year) {
                if (month > endMonth) {
                    inMonthRange = false;
                }
            }
            if (startYear == year) {
                if (month < startMonth) {
                    inMonthRange = false;
                }
            }
            boolean inDayRange = true;
            if (endMonth == month) {
                if (dayNum > endDayNum) {
                    inDayRange = false;
                }
            }
            if (startMonth == month) {
                if (dayNum < startDayNum) {
                    inDayRange = false;
                }
            }
            boolean sameDayOfWeek = day.getDayOfWeek() == startDay.getDayOfWeek();
            boolean sameDayNum = dayNum == startDayNum;
            Event.RepeatingType repeatingType = curEvent.getRepeatingType();

            switch (repeatingType) {
                case DAILY:
                    //As long as the date isn't before the start date or after the end date, add this event
                    if (inYearRange && inMonthRange && inDayRange) {
                        events.add(curEvent);
                    }
                    break;
                case WEEKLY:
                    //If the date is in range and the day of the week matches, add this event
                    if (inYearRange && inMonthRange && inDayRange && sameDayOfWeek) {
                        events.add(curEvent);
                    }
                    break;
                case MONTHLY:
                    //If the date is in range and the day number matches, add this event
                    if (inYearRange && inMonthRange && inDayRange && sameDayNum) {
                        events.add(curEvent);
                    }
                    break;
            }
        }

        return events;
    }

    public Event find(String id) {
        for (Event event: m_nonRepeatingEvents) {
            if (event.getID().equals(id)) {
                return event;
            }
        }

        Iterator it = m_repeatingEvents.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            List<Event> repeatingEvents = (List)pair.getValue();

            for (Event event: repeatingEvents) {
                if (event.getID().equals(id)) {
                    return event;
                }
            }
        }

        return null;
    }

    public void remove(Event event) throws NullPointerException {
        if (event == null) {
            throw new NullPointerException("Could not remove a null event.");
        }

        if (m_nonRepeatingEvents != null) {
            m_nonRepeatingEvents.remove(event);
        }
        if (m_repeatingEvents != null) {
            int key = event.getStartDay().getDayOfWeek();
            List<Event> events = m_repeatingEvents.get(key);
            if (events != null && events.size() != 0) {
                events.remove(event);
                m_repeatingEvents.put(key, events);
            }
        }
    }

    public List<Event.CategoryType> getCategories() {
        return m_eventCategories;
    }
}
