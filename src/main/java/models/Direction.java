package models;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;

/**
 * Class which holds the implementation for an XML document of the type direction.
 *
 * A direction element represents the direction in which a vehicle goes.
 * 1 represents going to, 0 represents coming from.
 *
 * A direction element is represented as follows in the XML document:
 * {@code
 * <direction way="1">
 *     <arrival>
 *         ...
 *     </arrival>
 *     ...
 *     <arrival>
 *         ...
 *     </arrival>
 * </direction>
 * }
 */
public class Direction {
    // Way [0,1] -> 0 tur, 1 retur
    @XmlAttribute(name = "way")
    public int way;
    @XmlElement(name = "arrival")
    public ArrayList<Arrival> arrivals;

    public Direction(){
    }

    /**
     * Constructor for the Direction class.
     *
     *
     * @param way Integer: 0 represents coming, 1 represents going.
     * @param arrivals ArrayList of type Arrival: Elements of the type arrival.
     */
    public Direction(int way, ArrayList<Arrival> arrivals){
        this.way = way;
        this.arrivals = arrivals;
    }
}