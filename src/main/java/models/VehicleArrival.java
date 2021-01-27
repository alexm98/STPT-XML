package models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "vehicle")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehicleArrival {
    @XmlElement(name = "vehicle-id")
    public final String vehicleId;
    @XmlElement(name = "arrival_time")
    public final Time arrivalTime;

    public VehicleArrival(String vehicleId, Time arrivalTime) {
        this.vehicleId = vehicleId;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "\nvehicleId='" + vehicleId + '\'' +
                ", arrivalTime=" + arrivalTime;
    }
}