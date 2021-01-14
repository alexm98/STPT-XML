package Models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Wrapper {
    @XmlElementWrapper(name="TransportStations")
    @XmlElement(name="TransportStation")
    private List<TransportStation> transport_stations;

    public Wrapper() {
        transport_stations = new ArrayList<TransportStation>();
    }

    public List<TransportStation> getArticles() {
        return this.transport_stations;
    }

    public void setArticles(List<TransportStation> transport_stations) {
        this.transport_stations = transport_stations;
    }
}

