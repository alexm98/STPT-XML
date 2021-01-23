package Models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
public class VehiclesWrapper {
    @XmlElementWrapper(name="vehicles")
    @XmlElement(name="vehicle")
    private List<Vehicle> vehicles;

    public VehiclesWrapper() {
        vehicles = new ArrayList<Vehicle>();
    }

    public List<Vehicle> getArticles() {
        return this.vehicles;
    }

    public void setArticles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}


