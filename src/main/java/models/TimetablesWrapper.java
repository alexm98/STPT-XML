package models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which holds the wrapper for the Timetable object.
 *
 * The wrapper is represented by the following type of element in the XML document:
 *
 * {@code
 * <timetables>
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
 *   ...
 *   <timetable vehicle_id="431">
 *       ...
 *   </timetable>
 * <timetables>
 * }
 */
@XmlRootElement(name="timetables-root")
@XmlAccessorType(XmlAccessType.FIELD)
public class TimetablesWrapper {
    @XmlElementWrapper(name="timetables")
    @XmlElement(name="timetable")
    private List<TimeTable> timeTables;

    public TimetablesWrapper() {
        timeTables = new ArrayList<TimeTable>();
    }

    public List<TimeTable> getArticles() {
        return this.timeTables;
    }

    public void setArticles(List<TimeTable> timetables) {
        this.timeTables = timetables;
    }
}