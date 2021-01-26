package core;

import models.TransportStation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Class which holds the implementation for interacting with a transport-station object.
 *
 * A transport-station element of of the following structure in the XML:
 *
 *  {@code
 *  <transport-station id="3583">
 *       <line-id>2406</line-id>
 *       <line-name>Tv9b</line-name>
 *       <raw-station-name>Bv Sudului_2</raw-station-name>
 *       <friendly-station-name>Bulevardul Sudului / Hotel Lido (AEM)</friendly-station-name>
 *       <short-station-name>Sudului</short-station-name>
 *       <junciton-name>Sudului</junciton-name>
 *       <lat>45.737211</lat>
 *       <long>21.250093</long>
 *       <invalid>0</invalid>
 *       <verified>dup script</verified>
 *       <verification-date>11.12.16.</verification-date>
 *       <gmaps-link>http://maps.google.com/maps?q=Bulevardul%20Sudului%20/%20Hotel%20Lido@45.737211,21.250093</gmaps-link>
 *       <info-comments>0</info-comments>
 *     </transport-station>}
 *
 *  Using the StationsInteractor class we can operate on such elements by parsing the XML document and using the
 *  XPathUtils class to query, delete, edit and add.
 */
public class StationsInteractor extends Interactor {

    /**
     * Constructor of the StationsInteractor class, which calls the parent class for creating the marshalled XML doc.
     *
     * @param path_to_doc Path to the XML document which will be used by the interactor.
     * @throws ParserConfigurationException @see ParserConfigurationException
     * @throws JAXBException @see JAXBException
     */
    public StationsInteractor(String path_to_doc) throws
            ParserConfigurationException,
            JAXBException {
        super(path_to_doc);
    }

    /**
     * Method for querying for all available transport-stations, taken from the parent XML document.
     *
     * The querying is done by passing the following xPath expression to the XPathUtils object:
     * "/transport-stations-root/transport-stations/transport-station"
     *
     *
     * @return NodeList: A list of Nodes representing all the matched elements found by the query.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public NodeList getAllStations() throws XPathExpressionException {
        return xputils.QueryXPath("/transport-stations-root/transport-stations/transport-station");
    }

    /**
     * Method for finding a transport-station based on a given id.
     *
     * The querying is done by passing the searched id in the following xPath expression, and passing the expression to
     * the XPathUtils class:
     * "//transport-station[@id=%s]"
     *
     * The transport station whose id matches the required id will be returned.
     *
     * @param station_id Integer: Searched transport station id.
     * @return Node: If the transport station with the requested id has been found, it will be returned.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node getStation(Integer station_id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//transport-station[@id=%s]", station_id)).item(0);
    }

    /**
     * Method which is used for creating a new element of the type transport station.
     *
     * This is achieved by using XPath for finding where to place the new transport station element, and creating it
     * based on the passed parameters. After creation, we append the new Element to the parent.
     *
     * @param new_id Integer: Id of the vehicle to be added. Example: 3306
     * @param lineID int: Id of the line for the transport station. Example: 1266.
     * @param lineName String: Name of the line. Example: Tv4.
     * @param stationID int: id of the station.
     * @param rawStationName String: Raw name for the station. Example: P-ta Crucii_2.
     * @param friendlyStationName String: Friendlier version of the raw station name.
     *                            Example: Piata Crucii (Torontalului)
     * @param shortStationName String: Shorter version for the station name. Example: P-ta Crucii.
     * @param junctionName String: Name of the junction. Example: P-ta Crucii.
     * @param x double: Latitude of the station location.
     * @param y double: Longitude of the station location.
     * @param is_invalid Boolean: States whether the station is still in use.
     * @param verif String: Method of the station is verified.
     * @param verif_date String: Date of the last verification.
     * @param gmaps_links String: Link for google maps location.
     * @param info_comm String: More info.
     * @return Returns a Node object which represents the newly added transport station element.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node createStation(Integer new_id, int lineID, String lineName, int stationID, String rawStationName,
                              String friendlyStationName, String shortStationName, String junctionName,
                              double x, double y, Boolean is_invalid, String verif,
                              String verif_date, String gmaps_links, String info_comm) throws XPathExpressionException{

        Element last_station = (Element) (xputils.QueryXPath("//transport-station[not(@id <= preceding-sibling::transport-station/@id) and not(@id <=following-sibling::transport-station/@id)]").item(0));
//        Element last_station = (Element) (xputils.QueryXPath("//transport-station/last()[@id]").item(0));

        if(new_id == null) {
            new_id = Integer.parseInt(last_station.getAttribute("id")) + 1;
        }

        Element new_station = this.document.createElement("transport-station");
        new_station.setAttribute("id", String.valueOf(new_id));

        Element line_id = this.document.createElement("line-id");
        line_id.setTextContent(String.valueOf(lineID));
        Element line_name = this.document.createElement("line-name");
        line_name.setTextContent(lineName);
        Element raw_station_name = this.document.createElement("raw-station-name");
        raw_station_name.setTextContent(rawStationName);
        Element friendly_station_name = this.document.createElement("friendly-station-name");
        friendly_station_name.setTextContent(friendlyStationName);
        Element short_station_name = this.document.createElement("short-station-name");
        short_station_name.setTextContent(shortStationName);
        Element junciton_name = this.document.createElement("junciton-name");
        junciton_name.setTextContent(junctionName);
        Element lat = this.document.createElement("lat");
        lat.setTextContent(String.valueOf(x));
        Element longitude = this.document.createElement("long");
        longitude.setTextContent(String.valueOf(y));
        Element invalid = this.document.createElement("invalid");
        invalid.setTextContent(String.valueOf(is_invalid));
        Element verified = this.document.createElement("verified");
        verified.setTextContent(verif);
        Element verification_date = this.document.createElement("verification-date");
        verification_date.setTextContent(verif_date);
        Element gmaps_link = this.document.createElement("gmaps-link");
        gmaps_link.setTextContent(gmaps_links);
        Element info_comments = this.document.createElement("info-comments");
        info_comments.setTextContent(info_comm);

        new_station.appendChild(line_id);
        new_station.appendChild(line_name);
        new_station.appendChild(raw_station_name);
        new_station.appendChild(friendly_station_name);
        new_station.appendChild(short_station_name);
        new_station.appendChild(junciton_name);
        new_station.appendChild(lat);
        new_station.appendChild(longitude);
        new_station.appendChild(invalid);
        new_station.appendChild(verified);
        new_station.appendChild(verification_date);
        new_station.appendChild(gmaps_link);
        new_station.appendChild(info_comments);

        last_station.getParentNode().appendChild(new_station);

        return new_station;
    }

    /**
     * Method for creating a new transport station which is used by JAXB binding.
     *
     * @param t TransportStation: TransportStation element representing the new element to be added.
     * @return Returns a Node element representing the newly added transport station element.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node createStation(TransportStation t) throws XPathExpressionException {
        return this.createStation(null, t.lineID, t.lineName, t.stationID, t.rawStationName, t.friendlyStationName,
                t.shortStationName, t.junctionName, t.lat, t.longitude, t.is_invalid, t.verified,
                t.verification_date, t.gmaps_links, t.info_comments);
    }

    /**
     * Method for replacing an element of type transport station with a new TransportStation, based on a given id.
     *
     * The querying to find the requested transport station to be replaced will be done by using the existent
     * getStation(Integer id) method. If the transport station is found, a new transport station is created with the
     * new requirements and the parent will now replace the old transport station with the new one.
     *
     * @param id Integer: id for finding the requested transport station.
     * @param t TransportStation: Replacement for the old transport station element.
     * @return Document: The XML document which has the requested transport station replaced.
     * @throws XPathExpressionException  @see XPathExpressionException
     */
    public Document replaceStation(Integer id, TransportStation t) throws XPathExpressionException {
        Node node_to_replace = this.getStation(id);
        Node new_au = this.createStation(t);
        node_to_replace.getParentNode().replaceChild(new_au, node_to_replace);

        return this.document;
    }

    /**
     * Method for deleting an element of type transport station based on a given id.
     *
     * The querying to find the transport station whose specific id is the requested one is done by using the existent
     * getVehicle(Integer id) method. If the transport station is found, a new transpor station is created with the
     * new requirements and the parent will now replace the old transport station with the new one.
     *
     * If the requested transport station is found, it will be removed from its parent in the XML document.

     * @param id Integer: id for finding the requested transport station to be deleted.
     * @return Document: The XML document which has the requested transport station deleted.
     * @throws XPathExpressionException @see XPathExpressionExceptiond
     */
    public Document deleteStation(Integer id) throws XPathExpressionException {
        Element station_to_delete = (Element) getStation(id);
        station_to_delete.getParentNode().removeChild(station_to_delete);

        return this.document;
    }
}