package Models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement(name = "Timetable")
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeTable {
    @XmlAttribute(name = "vehicle_id")
    public int vehicleID;
    @XmlElement(name = "direction")
    public ArrayList<Direction> direction;
}
