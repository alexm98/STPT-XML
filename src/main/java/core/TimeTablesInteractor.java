package core;

import Models.*;
import org.w3c.dom.Document;
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

    public Node getTimeTable(Integer vehicle_id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//timetable[@vehicle_id=%s]", vehicle_id)).item(0);
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

    public Node createTimeTable(int vehicle_id, ArrayList<Direction> directions) throws XPathExpressionException {
        Element last_timetable = (Element) (xputils.QueryXPath("//timetable[not(@vehicle_id <= preceding-sibling::timetable/@id) and not(@vehicle_id <=following-sibling::timetable/@vehicle_id)]").item(0));


        Element new_timetable = this.document.createElement("timetable");
        new_timetable.setAttribute("vehicle_id", String.valueOf(vehicle_id));

        for(Direction d : directions){
            Element dir = (Element) this.createDirection(d.way, d.arrivals);
            new_timetable.appendChild(dir);
        }

        last_timetable.getParentNode().appendChild(new_timetable);

        return new_timetable;
    }

    public Node createTimeTable(TimeTable t) throws XPathExpressionException {
        return this.createTimeTable(t.vehicleID, t.direction);
    }

    public Document replaceTimeTable(Integer id, TimeTable t) throws XPathExpressionException {
        Node node_to_replace = this.getTimeTable(id);
        Node new_timetable = this.createTimeTable(t);
        node_to_replace.getParentNode().replaceChild(new_timetable, node_to_replace);

        return this.document;
    }

    public Document deleteTimeTable(Integer id) throws XPathExpressionException {
        Element timetable_to_delete = (Element) getTimeTable(id);
        timetable_to_delete.getParentNode().removeChild(timetable_to_delete);

        return this.document;
    }
}
