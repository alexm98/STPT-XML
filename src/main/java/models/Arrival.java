package models;


import javax.xml.bind.annotation.XmlElement;

public class Arrival {
    @XmlElement(name = "station")
    public TransportStation station;
    @XmlElement(name = "time")
    public Time time;

    public Arrival(){
    }

    public Arrival(TransportStation station, Time t){
        this.station = station;
        this.time = t;
    }

    public String toString(){
        return this.time.toString() + " " + this.station.toString();
    }
}
