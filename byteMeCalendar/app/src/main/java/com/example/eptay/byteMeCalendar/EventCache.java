package com.example.eptay.byteMeCalendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventCache {
    private static EventCache instance;
    List<Event> m_nonRepeatingEvents = new ArrayList<>();
    HashMap<Integer, List<Event>> m_repeatingEvents = new HashMap();
    List<EventCategory> m_eventCategories = new ArrayList<>();

    static {
        instance = new EventCache();
    }

    public static EventCache getInstance() {
        return instance;
    }

    public void add(Event event) {
        if (event.isRepeating()) {
            Integer dayOfWeek = event.getStartDay().getDayOfWeek();
            List<Event> eventsForDay = m_repeatingEvents.get(dayOfWeek);
            eventsForDay.add(event);
            m_repeatingEvents.put(dayOfWeek, eventsForDay);
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
            else if (startYear == year) {
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
            else if (startMonth == month) {
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

    public List<EventCategory> getCategories() {
        return m_eventCategories;
    }
}
