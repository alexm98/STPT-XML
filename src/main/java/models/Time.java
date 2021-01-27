package models;

import javax.xml.bind.annotation.XmlValue;

/**
 * Class which holds the implementation for time handling.
 *
 * Time is used by the timetable element, through the arrival element.
 */
public class Time {
    @XmlValue
    public String time;

    public Time(){
    }

    /**
     * Method for comparing two objects of the type Time.
     *
     * @param time1 Time: A Time object of the form: hh:mm.
     * @param time2 Time: A Time object of the form: hh:mm.
     * @return int: 0 if equality,
     *              1 if time1 > time2
     *              -1 if time1 < time2
     */
    public int compareTime(String time1, String time2) {
        String[] splitTime1 = time1.split(":");
        String[] splitTime2 = time2.split(":");

        int time1Hour = Integer.parseInt(splitTime1[0]);
        int time1Minutes = Integer.parseInt(splitTime1[1]);

        int time2Hour = Integer.parseInt(splitTime1[0]);
        int time2Minutes = Integer.parseInt(splitTime1[1]);

        if (time1Hour > time2Hour) {
            return -1;
        } else if (time1Minutes > time2Minutes) {
            return 1;
        } else {
            return 0;
        }
    }
    public Time(String time){
        this.time = time;
    }

    public String toString(){
        return this.time;
    }
}