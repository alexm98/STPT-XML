package models;


import javax.xml.bind.annotation.XmlElement;

/**
 * Class which holds the implementation for an XML element of the type arrival.
 *
 * An arrival element represents the time when a transport vehicle arrives at a specific transport station, and it is
 * represented as follows in the XML document:
 *
 * {@code
 * <arrival>
 *      <station id="2702">Regele Carol</station>
 *      <time>15:43</time>
 * </arrival>
 * }
 */
public class Arrival {
    @XmlElement(name = "station")
    public TransportStation station;
    @XmlElement(name = "time")
    public Time time;

    public Arrival(){
    }

    /**
     * Constructor for the Arrival class.
     *
     * @param station TransportStation: An object representing the transport-station where a vehicle arrives.
     * @param t Time: Time of arrival for that vehicle.
     */
    public Arrival(TransportStation station, Time t){
        this.station = station;
        this.time = t;
    }

    public String toString(){
        return this.time.toString() + " " + this.station.toString();
    }
}