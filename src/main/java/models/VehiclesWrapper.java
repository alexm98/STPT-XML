package models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which holds the wrapper for the Vehicle object.
 */
@XmlRootElement(name="vehicles-root")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehiclesWrapper {
    @XmlElementWrapper(name="vehicles")
    @XmlElement(name="vehicle")
    private List<Vehicle> vehicles;

    /**
     * Constructor of the VehicleWrapper class.
     * Creates the vehicles list.
     */
    public VehiclesWrapper() {
        vehicles = new ArrayList<Vehicle>();
    }

    /**
     * Method which returns all the vehicles which appear in the XML document.
     * @return All found elements of type Vehicle.
     */
    public List<Vehicle> getArticles() {
        return this.vehicles;
    }

    /**
     * Set a list of vehicles.
     * @param vehicles A list of elements of type Vehicle.
     */
    public void setArticles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}