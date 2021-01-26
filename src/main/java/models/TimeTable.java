package models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

/**
 * Class which holds the implementation for an XML element of the type timetable.
 *
 * A timetable element is responsible of holding information about the directions and arrival for a specific vehicle.
 * It is represented as follows in the XML document:
 *
 * {@code
 * <timetable vehicle_id="1207">
 *      <direction way="1">
 *          <arrival>
 *              ...
 *          </arrival>
 *          ...
 *          <arrival>
 *              ...
 *          </arrival>
 *       </direction>
 *       <direction way="0">
 *          <arrival>
 *              ...
 *          </arrival>
 *          ...
 *          <arrival>
 *              ...
 *          </arrival>
 *       </direction>
 *   </timetable>
 * }
 */
@XmlRootElement(name = "timetable")
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeTable {
    @XmlAttribute(name = "vehicle_id")
    public int vehicleID;
    @XmlElement(name = "direction")
    public ArrayList<Direction> direction;
}