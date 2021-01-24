package Models;


import javax.xml.bind.annotation.XmlElement;

public class Arrival {
    @XmlElement(name = "station")
    public TransportStation station;
    @XmlElement(name = "time")
    public Time time;
}
