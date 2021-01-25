package core;

import models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;

/**
 * Class which holds the implementation for interacting with a timetable object.
 *
 * A timetable element of of the following structure in the XML:
 *
 * {@code
 * <timetable vehicle_id="1207">
 *     <direction way="1">
 *         <arrival>
 *             <station id="4483">Gara de Nord</station>
 *             <time>15:39</time>
 *          </arrival>
 *     </direction>
 * </timetable>
 * }
 *
 * Using the TimeTablesInteractor class we can operate on such elements by parsing the XML document and using the
 * XPathUtils class to query, delete, edit and add.
 */
public class TimeTablesInteractor extends Interactor {

    /**
     * Constructor of the TimeTablesInteractor class, which calls the parent class for creating the marshalled XML doc.
     *
     * @param path_to_doc Path to the XML document which will be used by the interactor.
     * @throws ParserConfigurationException @see ParserConfigurationException
     * @throws JAXBException @see JAXBException
     */
    public TimeTablesInteractor(String path_to_doc) throws
            ParserConfigurationException,
            JAXBException {
        super(path_to_doc);
    }

    /**
     * Method for querying for all available timetables, taken from the parent XML document.
     *
     * The querying is done by passing the following xPath expression to the XPathUtils object:
     * "/timetables-root/timetables/timetable"
     *
     * @return A list of Nodes representing all the matched elements found by the query.
     * @throws XPathExpressionException  @see XPathExpressionException
     */
    public NodeList getAllTimeTables() throws XPathExpressionException {
        return xputils.QueryXPath("/timetables-root/timetables/timetable");
    }

    /**
     * Method for finding a timetable based on a given id.
     *
     * The querying is done by passing the searched id in the following xPath expression, and passing the expression to
     * the XPathUtils class:
     * "//timetable[@vehicle_id=%s]"
     *
     * The timetable whose id matches the required id will be returned.
     *
     * @param vehicle_id Integer: Searched timetable id.
     * @return If the timetable with the requested id has been found, it will be returned.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node getTimeTable(Integer vehicle_id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//timetable[@vehicle_id=%s]", vehicle_id)).item(0);
    }

    /**
     * Method which is used for creating a new element of the type arrival.
     *
     * This is achieved by creating a new element of type arrival and adding it to the timetable of the searched id.
     *
     * @param station_id int: id of the station. Example: 4483.
     * @param station_name String: Name of the station. Example: Gara de Nord.
     * @param arrives_in Time: Time of arrival. Example: 16:05
     * @return Returns a Node object which represents the newly added arrival element.
     */
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

    /**
     * Method which is used for creating a new element of the type direction.
     *
     * This is achieved by creating a new element of type direction and adding it to the timetable of the searched id.
     *
     * @param way Integer: 0 represents coming, 1 represents going.
     * @param arrivals ArrayList of type Arrival: Elements of the type arrival.
     * @return Returns a Node object which represents the newly added direction element.
     */
    public Node createDirection(Integer way, ArrayList<Arrival> arrivals){
        Element direction = this.document.createElement("direction");
        direction.setAttribute("way", way.toString());

        for(Arrival a: arrivals){
            Element arr = (Element) this.createArrival(a.station.stationID, a.station.shortStationName, a.time);
            direction.appendChild(arr);
        }

        return direction;
    }

    /**
     * Method which is used for creating a new element of the type timetable.
     *
     * This is achieved by finding where to add the new timetable element in the XML document, using the following
     * query in the XPathUtils object:
     * {@code "//timetable[not(@vehicle_id <= preceding-sibling::timetable/@id) and not(@vehicle_id <=following-sibling::timetable/@vehicle_id)]"}
     *
     * We then create the vehicle id and the directions for that vehicle. We now need to only populate the directions
     * with arrivals.
     *
     * @param vehicle_id Integer: id of the vehicle for which the timetable is created.
     *                   Example: 1207.
     * @param directions ArrayList of type Direction: Possible directions for the vehicle.
     * @return Returns a Node object which represents the newly added timetable element.
     * @throws XPathExpressionException @see XPathExpressionException
     */
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

    /**
     * Method for creating a new timetable which is used by JAXB binding.
     * @param t TimeTable: TimeTable element representing the new element to be added.
     * @return Returns a Node element representing the newly added timetable element.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node createTimeTable(TimeTable t) throws XPathExpressionException {
        return this.createTimeTable(t.vehicleID, t.direction);
    }

    /**
     * Method for replacing an element of type timetable with a new TimeTable, based on a given id.
     *
     * The querying is done by searching for the timetable to be updated with the existing method getTimeTable(). If
     * the timetable is found, we create a new timetable from t and we update the parent with the new node.
     *
     * @param id Integer: id for finding the requested timetable.
     * @param t TimeTable: Replacement for the old timetable element.
     * @return Document: The XML document which has the requested timetable replaced.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Document replaceTimeTable(Integer id, TimeTable t) throws XPathExpressionException {
        Node node_to_replace = this.getTimeTable(id);
        Node new_timetable = this.createTimeTable(t);
        node_to_replace.getParentNode().replaceChild(new_timetable, node_to_replace);

        return this.document;
    }

    /**
     * Method for deleting an element of type timetable based on a given id.
     *
     * The querying to find the timetable whose specific id is the requested one is done by using the existent
     * getTimeTable(Integer id) method. If the timetable is found, a new timetable is created with the new requirements
     * and the parent will now replace the old timetable with the new one.
     *
     * If the requested timetable is found, it will be removed from its parent in the XML document.
     *
     * @param id Integer: id for finding the requested timetable to be deleted.
     * @return Document: The XML document which has the requested timetable deleted.
     * @throws XPathExpressionException @see XPathExpressionExceptiond
     */
    public Document deleteTimeTable(Integer id) throws XPathExpressionException {
        Element timetable_to_delete = (Element) getTimeTable(id);
        timetable_to_delete.getParentNode().removeChild(timetable_to_delete);

        return this.document;
    }
}