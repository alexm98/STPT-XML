package Models;

import javax.xml.bind.annotation.*;

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

    public Vehicle(int id, String name, String type){
        this.vehicleID = id;
        this.vehicleName = name;
        this.vehicleType = type;
    }

    public String toString(){
        return "Vehicle{" +
                "id=" + this.vehicleID +
                ", name=" + this.vehicleName +
                ", type=" + this.vehicleType + "}";
    }
}
