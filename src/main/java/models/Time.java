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

    public Time(String time){
        this.time = time;
    }

    public String toString(){
        return this.time;
    }
}