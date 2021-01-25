package models;

import javax.xml.bind.annotation.XmlValue;

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
