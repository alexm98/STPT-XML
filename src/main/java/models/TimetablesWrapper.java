package models;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

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


