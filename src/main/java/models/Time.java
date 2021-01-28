package models;

import javax.xml.bind.annotation.XmlValue;

/**
 * Class which holds the implementation for time handling.
 * <p>
 * Time is used by the timetable element, through the arrival element.
 */
public class Time {
    @XmlValue
    public String time;

    public Time() {
    }

    /**
     * Method for comparing two objects of the type Time.
     *
     * @param time1 Time: A Time object of the form: hh:mm.
     * @param time2 Time: A Time object of the form: hh:mm.
     * @return int: 0 if equality,
     * 1 if time1 bigger time2
     * -1 if time1 smaller time2
     */
    public int compareTime(String time1, String time2) {
        String[] splitTime1 = time1.split(":");
        String[] splitTime2 = time2.split(":");

        if (!(isInteger(splitTime1[0]) &&
                isInteger(splitTime1[1]) &&
                isInteger(splitTime2[0]) &&
                isInteger(splitTime2[1]))) {
            return 2;
        }

        int time1Hour = Integer.parseInt(splitTime1[0]);
        int time1Minutes = Integer.parseInt(splitTime1[1]);

        int time2Hour = Integer.parseInt(splitTime2[0]);
        int time2Minutes = Integer.parseInt(splitTime2[1]);

        if (time1Hour >= time2Hour) {
            return 1;
        } else if (time1Minutes >= time2Minutes) {
            return 1;
        } else {
            return -1;
        }
    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Time(String time) {
        this.time = time;
    }

    public String toString() {
        return this.time;
    }
}