package com.example.eptay.byteMeCalendar;

public class Day {

        private String dayName;
        private String date;
        private int eventCount;

        public Day(String dayName, String date, int eventCount) {
            this.dayName = dayName;
            this.date = date;
            this.eventCount = eventCount;
        }

        public String getDayName() {
            return dayName;
        }

        public void setDayName(String dayName) {
            this.dayName = dayName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getEventCount() {
            return eventCount;
        }

        public void setEventCount(int eventCount) {
            this.eventCount = eventCount;
        }
    }


