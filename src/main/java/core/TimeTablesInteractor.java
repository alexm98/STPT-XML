package core;

import Models.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;

public class TimeTablesInteractor extends Interactor {

    public TimeTablesInteractor(String path_to_doc) throws
            ParserConfigurationException,
            JAXBException {
        super(path_to_doc);
    }

    public NodeList getAllTimeTables() throws XPathExpressionException {
        return xputils.QueryXPath("/timetables-root/timetables/timetable");
    }

    public NodeList getTimeTable(Integer vehicle_id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//timetable[@vehicle_id=%s]", vehicle_id));
    }

    public Node createArrival(int station_id, String station_name, Time arrives_in){
        Element arrival = this.document.createElement("arrival");
        Element station = this.document.createElement("station");
        station.setAttribute("id", String.valueOf(station_id));
        station.setTextContent(station_name);
        Element time = this.document.createElement("time");
        time.setTextContent(arrives_in.toString());

        arrival.appendChild(station);
        arrival.appendChild(time);

        return arrival;
    }

    public Node createDirection(Integer way, ArrayList<Arrival> arrivals){
        Element direction = this.document.createElement("direction");
        direction.setAttribute("way", way.toString());

        for(Arrival a: arrivals){
            Element arr = (Element) this.createArrival(a.station.stationID, a.station.shortStationName, a.time);
            direction.appendChild(arr);
        }

        return direction;
    }

    public Node createTimeTable(Integer new_id, int vehicle_id, ArrayList<Direction> directions) throws XPathExpressionException {
        // get last vehicle ID, create a new node and add it to it's parent
        Element last_timetable = (Element) (xputils.QueryXPath("//timetable[not(@vehicle_id <= preceding-sibling::vehicle/@id) and not(@id <=following-sibling::timetable/@vehicle_id)]").item(0));

        if(new_id == null) {
            new_id = Integer.parseInt(last_timetable.getAttribute("id")) + 1;
        }

        Element new_timetable = this.document.createElement("timetable");
        new_timetable.setAttribute("vehicle_id", new_id.toString());

        for(Direction d : directions){
            Element dir = (Element) this.createDirection(d.way, d.arrivals);
            last_timetable.appendChild(dir);
        }

        last_timetable.getParentNode().appendChild(new_timetable);

        return new_timetable;
    }
}
