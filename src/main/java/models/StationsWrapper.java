package models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which holds the wrapper for objects of type TransportStation.
 *
 * The wrapper is represented by the following type of element in the XML document:
 *
 {@code
  * <transport-stations>
  *     <transport-station>
  *         ...
  *     </transport-station>
  *     ...
  *     <transport-station>
  *  *         ...
  *  *  </transport-station>
  * </transport-stations>
  * }
 */
@XmlRootElement(name="transport-stations-root")
@XmlAccessorType(XmlAccessType.FIELD)
public class StationsWrapper {
    @XmlElementWrapper(name="transport-stations")
    @XmlElement(name="transport-station")
    private List<TransportStation> transport_stations;

    /**
     * Constructor of the StationsWrapper class.
     */
    public StationsWrapper() {
        transport_stations = new ArrayList<TransportStation>();
    }

    /**
     * Method which returns all the objects of type transport-station which appear in the XML document.
     *
     * @return A list composed of TransportStation objects.
     */
    public List<TransportStation> getArticles() {
        return this.transport_stations;
    }

    /**
     * Set the TransportStation list with a given one.
     *
     * @param transport_stations A list of elements of the type TransportStation.
     */
    public void setArticles(List<TransportStation> transport_stations) {
        this.transport_stations = transport_stations;
    }
}