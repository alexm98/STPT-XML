package models;

import javax.xml.bind.annotation.*;

/**
 * Class which holds the implementation for a vehicle object.
 *
 * A vehicle is represented as follows in the XML document:
 *
 * {@code
 *  <vehicle id="3306">
 *      <vehicle-name>M41</vehicle-name>
 *      <vehicle-type>Bus</vehicle-type>
 *  </vehicle>
 * }
 *
 * Operations of the type edit, add, delete and query involving the vehicle element will be done using the
 * Vehicle Interactor class.
 */
@XmlRootElement(name = "vehicle")
@XmlAccessorType(XmlAccessType.FIELD)
public class Vehicle {
    @XmlAttribute(name = "id")
    public int vehicleID;
    @XmlElement(name = "vehicle-name")
    public String vehicleName;
    @XmlElement(name = "vehicle-type")
    public String vehicleType;

    public Vehicle(){
    }

    /**
     * Constructor for the Vehicle class.
     *
     * @param id Unique id for the vehicle object.
     * @param name Name of the vehicle.
     * @param type Type of the vehicle.
     */
    public Vehicle(int id, String name, String type){
        this.vehicleID = id;
        this.vehicleName = name;
        this.vehicleType = type;
    }

    /**
     * Override of string form for a vehicle object.
     *
     * @return Pretty printed format of a vehicle instance.
     */
    public String toString(){
        return "Vehicle{" +
                "id=" + this.vehicleID +
                ", name=" + this.vehicleName +
                ", type=" + this.vehicleType + "}";
    }
}