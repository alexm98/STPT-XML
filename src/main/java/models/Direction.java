package models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

public class Direction {
    // Way [0,1] -> 0 tur, 1 retur
    @XmlAttribute(name = "way")
    public int way;
    @XmlElement(name = "arrival")
    public ArrayList<Arrival> arrivals;

    public Direction(){
    }

    public Direction(int way, ArrayList<Arrival> arrivals){
        this.way = way;
        this.arrivals = arrivals;
    }
}
