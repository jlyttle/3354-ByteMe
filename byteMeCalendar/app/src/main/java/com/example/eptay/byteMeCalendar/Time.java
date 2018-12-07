package com.example.eptay.byteMeCalendar;

/*
    Class for creating a Time object that will be used within the Hour list adapter in dayview
 */
    public class Time {

        /* MEMBER VARIABLES */
        private String hour;
        private String title;
        private String desc;

        /* METHODS */
        public Time(String hour, String title, String desc) {
            this.hour = hour;
            this.title = title;
            this.desc = desc;
        }

        public String getHour() {
            return hour;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }



