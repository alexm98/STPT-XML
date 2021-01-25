package models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="transport-stations-root")
@XmlAccessorType(XmlAccessType.FIELD)
public class StationsWrapper {
    @XmlElementWrapper(name="transport-stations")
    @XmlElement(name="transport-station")
    private List<TransportStation> transport_stations;

    public StationsWrapper() {
        transport_stations = new ArrayList<TransportStation>();
    }

    public List<TransportStation> getArticles() {
        return this.transport_stations;
    }

    public void setArticles(List<TransportStation> transport_stations) {
        this.transport_stations = transport_stations;
    }
}

