package core;

import models.Time;
import models.TransportStation;
import models.VehicleArrival;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class which implements an ad-hoc API adhering all the interactors to
 * provide more useful computations.
 */
public class WebService {
    private StationsInteractor stationsInteractor;
    private TimeTablesInteractor timeTablesInteractor;
    private VehiclesInteractor vehiclesInteractor;

    /**
     * Constructor of the WebService class.
     *
     * @param path_to_vehicles   Location of the vehicles XML document to be used.
     * @param path_to_timetables Location of the timetables XML document to be used.
     * @param path_to_stations   Location of the stations XML document to be used.
     */
    public WebService(String path_to_vehicles, String path_to_timetables, String path_to_stations) throws JAXBException, ParserConfigurationException {
        this.stationsInteractor = new StationsInteractor(path_to_stations);
        this.timeTablesInteractor = new TimeTablesInteractor(path_to_timetables);
        this.vehiclesInteractor = new VehiclesInteractor(path_to_vehicles);
    }

    /**
     * Method for retrieving the last vehicle departing from the required station.
     *
     * @param stationId String: Id representing the station to be queried.
     * @return Returns a Node element representing the time the last vehicle will leave from stationId.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node getLastDepartureVehicle(String stationId) throws XPathExpressionException, TransformerConfigurationException, ParserConfigurationException {
        NodeList departuresStation = this.getAllDepartures(stationId).getChildNodes();
        Time furthestTime = new Time("05:00"); // We assume that the earliest possible for a bus to start is 05:00.
        Node lastVehicle = null;

        for (int i = 0; i < departuresStation.getLength(); i++) {
            Node currentNode = departuresStation.item(i);

            if (currentNode.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element departureStation = (Element) departuresStation.item(i);
            String dsTimeStr = departureStation.getElementsByTagName("departure-time").item(0).getTextContent();
            Time dsTime = new Time(dsTimeStr);

            if(furthestTime.compareTime(dsTime.toString(), furthestTime.toString()) == 1) {
                System.out.println("DT: " + dsTimeStr + "; FT: " + furthestTime.toString());
                furthestTime = dsTime;

                String vehicleId = departureStation.getElementsByTagName("vehicle-id").item(0).getTextContent();
                lastVehicle = vehiclesInteractor.getVehicle(Integer.parseInt(vehicleId));
                lastVehicle = this.appendNode(lastVehicle, "departure-time", dsTimeStr);
            }
        }

        return lastVehicle;

    }

    /**
     * Method for retrieving the last vehicle arriving from the required station.
     *
     * @param stationId String: Id representing the station to be queried.
     * @return Returns a Node element representing the time the last vehicle will arrive at stationId.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node getLastArrivalVehicle(String stationId) throws XPathExpressionException, TransformerConfigurationException, ParserConfigurationException {
        NodeList arrivalsStation = this.getAllArrivals(stationId).getChildNodes();
        Node lastVehicle = null;
        Time closestTime = new Time("05:00"); // We assume that the latest possible for a bus to start is 05:00.

        for (int i = 0; i < arrivalsStation.getLength(); i++) {
            Node currentNode = arrivalsStation.item(i);

            if (currentNode.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element arrivalStation = (Element) arrivalsStation.item(i);
            String dsTimeStr = arrivalStation.getElementsByTagName("arrival-time").item(0).getTextContent();
            Time dsTime = new Time(dsTimeStr);

            if(closestTime.compareTime(dsTime.toString(), closestTime.toString()) == 1) {
                System.out.println("before DT: " + dsTimeStr + "; FT: " + closestTime.toString());
                closestTime = dsTime;

                String vehicleId = arrivalStation.getElementsByTagName("vehicle-id").item(0).getTextContent();
                lastVehicle = vehiclesInteractor.getVehicle(Integer.parseInt(vehicleId));
                lastVehicle = this.appendNode(lastVehicle, "arrival-time", dsTimeStr);
            }

            System.out.println("DT: " + dsTimeStr + "; FT: " + closestTime.toString());
        }

        return lastVehicle;

    }

    /**
     * Method for retrieving all the vehicles which at a moment will arrive at station s.
     *
     * @param s TransportStation: Transport element representing station we want to query for.
     * @return Returns a NodeList representing the vehicles that arrive at station s.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public NodeList getAllVehiclesPassingWithDestination(TransportStation s) {
        return null;
    }

    /**
     * Method for returning all arrivals for a given station id.
     *
     * @param stationId String: id of the station for getting all arrivals.
     * @return Node: A XML Node containing populated with vehicle-id and arrival-times.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node getAllArrivals(String stationId) throws XPathExpressionException, TransformerConfigurationException {
        NodeList timetables = timeTablesInteractor.getAllTimeTables();
        ArrayList<VehicleArrival> vehicleArrivals = new ArrayList<>();

        for (int i = 0; i < timetables.getLength(); i++) {
            Node currentNode = timetables.item(i);

            if (currentNode.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element currentTimetable = (Element) timetables.item(i);
            String vehichleId = currentTimetable.getAttribute("vehicle_id");

            Element arrivalDirection = this.getChildWith("way",
                    "1",
                    currentTimetable.getChildNodes());

            if (arrivalDirection == null) {
                continue;
            }

            ArrayList<Time> arrivalTimes = this.getAllArrivalsFrom(stationId, arrivalDirection.getChildNodes());

            for (Time time : arrivalTimes) {
                vehicleArrivals.add(new VehicleArrival(vehichleId, time));
            }
        }

        Node xmlResponse = this.transformToXML(vehicleArrivals,
                "vehicle-arrivals",
                "vehicle-arrival",
                "vehicle-id",
                "arrival-time"
        );
        this.writeToXML(xmlResponse);
        return xmlResponse;
    }

    /**
     * Method for returning all departures for a given station id.
     *
     * @param stationId String: id of the station for getting all departures.
     * @return Node: A XML Node containing populated with vehicle-id and departure-times.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node getAllDepartures(String stationId) throws XPathExpressionException, TransformerConfigurationException {
        NodeList timetables = timeTablesInteractor.getAllTimeTables();
        ArrayList<VehicleArrival> vehicleArrivals = new ArrayList<>();

        for (int i = 0; i < timetables.getLength(); i++) {
            Node currentNode = timetables.item(i);

            if (currentNode.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element currentTimetable = (Element) timetables.item(i);
            String vehichleId = currentTimetable.getAttribute("vehicle_id");

            Element arrivalDirection = this.getChildWith("way",
                    "0",
                    currentTimetable.getChildNodes());

            if (arrivalDirection == null) {
                continue;
            }

            ArrayList<Time> arrivalTimes = this.getAllArrivalsFrom(stationId, arrivalDirection.getChildNodes());

            for (Time time : arrivalTimes) {
                vehicleArrivals.add(new VehicleArrival(vehichleId, time));
            }
        }

        Node xmlResponse = this.transformToXML(vehicleArrivals,
                "vehicle-departures",
                "vehicle-departure",
                "vehicle-id",
                "departure-time"
                );

        this.writeToXML(xmlResponse);
        return xmlResponse;
    }

    private ArrayList<Time> getAllArrivalsFrom(String requestedStationId, NodeList nodeList) {
        ArrayList<Time> allArrivalTimes = new ArrayList<Time>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);

            if (currentNode.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element currentArrival = (Element) nodeList.item(i);

            Element currentStation = (Element) currentArrival.getElementsByTagName("station").item(0);
            String currentStationId = currentStation.getAttribute("id");

            if (currentStationId.equals(requestedStationId)) {
                String t = currentArrival.getElementsByTagName("time").item(0).getTextContent();
                Time currentTime = new Time(t);
                allArrivalTimes.add(currentTime);
            }
        }

        return allArrivalTimes;
    }

    private Element getChildWith(String attribute, String requestedValue, NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);

            if (currentNode.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element currentElement = (Element) nodeList.item(i);
            String elementAttributeValue = currentElement.getAttribute(attribute);

            if (elementAttributeValue.equals(requestedValue))
                return currentElement;
        }
        return null;
    }

    /**
     * Method for retrieving the time when the first vehicle departs from a station.
     *
     * @param s TransportStation: Transport element representing station we want to query for.
     * @return Returns a Node element representing the time the first vehicle will be at station s.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node getNextVehicleByTime(String stationId) throws XPathExpressionException {
        return null;
    }

    /**
     * Method for retrieving the closest transport station element.
     * <p>
     * As input we get the latitude and longitude given by the user in order to find the closest transport station.
     *
     * @param currentLatitude  double: Latitude of the current place.
     * @param currentLongitude double: Longitude of the current place.
     * @return Returns a Node element representing the closest transport station by currentLatitude and currentLongitude.
     * @throws XPathExpressionException @see XPathExpressionException
     */

    public Node getClosestStation(double currentLatitude, double currentLongitude) throws XPathExpressionException {
        NodeList allStations = stationsInteractor.getAllStations();
        Node closestStation = null;
        double shortestDistance = Integer.MAX_VALUE;

        for (int i = 0; i < allStations.getLength(); i++) {
            if (allStations.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element currentElement = (Element) allStations.item(i);

                if (currentElement.getNodeName().contains("transport-station")) {
                    double stationLatitude =
                            Double.parseDouble(currentElement.getElementsByTagName("lat").item(0).getTextContent());
                    double stationLongitude =
                            Double.parseDouble(currentElement.getElementsByTagName("long").item(0).getTextContent());

                    double distance =
                            this.calculateDistance(currentLatitude, currentLongitude, stationLatitude, stationLongitude);

                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        closestStation = currentElement;
                    }
                }
            }
        }

        return closestStation;
    }

    private double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        return Math.sqrt((latitude2 - latitude1) * (latitude2 - latitude1) + (longitude2 - longitude1) * (longitude2 - longitude1));
    }

    /**
     * Method for transforming an array list populated with objects of type VehicleArrival to XML form.
     * <p>
     * The conversion is needed as support for XML display for the web service.
     *
     * @param vehicleArrivals {@code ArrayList<VehicleArrival>}: An array list of VehicleArrival elements.
     * @return Node: The list of vehicle arrivals transformed to XML format.
     */
    public Node transformToXML(ArrayList<VehicleArrival> vehicleArrivals,
                               String rootName,
                               String mainElementsName,
                               String firstChildName,
                               String lastChildName) {

        try {

            DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder build = dFact.newDocumentBuilder();
            Document doc = build.newDocument();

            Element root = doc.createElement(rootName);
            doc.appendChild(root);

            for (VehicleArrival va : vehicleArrivals) {
                Element vaElement = doc.createElement(mainElementsName);
                root.appendChild(vaElement);

                Element vehicle_id = doc.createElement(firstChildName);
                vehicle_id.appendChild(doc.createTextNode(va.vehicleId));
                vaElement.appendChild(vehicle_id);

                Element arrival_time = doc.createElement(lastChildName);
                arrival_time.appendChild(doc.createTextNode(va.arrivalTime.toString()));
                vaElement.appendChild(arrival_time);
            }

            return root;

        } catch (ParserConfigurationException ex) {
            System.out.println("Error building document");
        }

        return null;
    }

    public void writeToXML(Node root) throws TransformerConfigurationException {
        // Save the document to the disk file
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFactory.newTransformer();

        // format the XML nicely
        aTransformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

        aTransformer.setOutputProperty(
                "{http://xml.apache.org/xslt}indent-amount", "4");
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");


        DOMSource source = new DOMSource(root);
        try {
            FileWriter fos = new FileWriter("test.xml");
            StreamResult result = new StreamResult(fos);
            aTransformer.transform(source, result);

        } catch (IOException | TransformerException e) {

            e.printStackTrace();
        }
    }

    public Node appendNode(Node parentNode,
                           String elementName,
                           String elementValue) throws ParserConfigurationException {
        Document doc = parentNode.getOwnerDocument();

        Element newElement = doc.createElement(elementName);
        newElement.appendChild(doc.createTextNode(elementValue));
        parentNode.appendChild(newElement);

        return parentNode;
    }

}