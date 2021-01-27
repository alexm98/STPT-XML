package core;

import models.Time;
import models.TransportStation;
import models.VehicleArrival;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
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
     * @param path_to_vehicles Location of the vehicles XML document to be used.
     * @param path_to_timetables Location of the timetables XML document to be used.
     * @param path_to_stations Location of the stations XML document to be used.
     */
    public WebService(String path_to_vehicles, String path_to_timetables, String path_to_stations) throws JAXBException, ParserConfigurationException {
        this.stationsInteractor = new StationsInteractor(path_to_stations);
        this.timeTablesInteractor = new TimeTablesInteractor(path_to_timetables);
        this.vehiclesInteractor = new VehiclesInteractor(path_to_vehicles);
    }

    /**
     * Method for retrieving the time when the last vehicle departs from a station.
     *
     * @param s TransportStation: Transport element representing station we want to query for.
     * @return Returns a Node element representing the time the last vehicle will be at station s.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node getLastVehicleForStation(Integer id) throws XPathExpressionException {
        return this.stationsInteractor.getStation(id);
    }

    /**
     * Method for retrieving all the vehicles which at a moment will arrive at station s.
     *
     * @param s TransportStation: Transport element representing station we want to query for.
     * @return Returns a NodeList representing the vehicles that arrive at station s.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public NodeList getAllVehiclesPassingWithDestination(TransportStation s){
        return null;
    }

    /**
     * Method for returning all arrivals in the form of (vehicleId, time) for a given station id.
     *
     * @param stationId String: id of the station for getting all arrivals.
     * @return {@code ArrayList<VehicleArrival>}: All pairs of vehicle-id and time which are arriving at the given
     *                                            station_id.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public ArrayList<VehicleArrival> getAllArrivals(String stationId) throws XPathExpressionException {
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

            for(Time time: arrivalTimes) {
                vehicleArrivals.add(new VehicleArrival(vehichleId, time));
            }
        }
        System.out.println("Done");
        return vehicleArrivals;
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

            if(currentStationId.equals(requestedStationId)) {
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
     *
     * As input we get the latitude and longitude given by the user in order to find the closest transport station.
     *
     * @param currentLatitude double: Latitude of the current place.
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
        return Math.sqrt((latitude2-latitude1)*(latitude2-latitude1) + (longitude2-longitude1)*(longitude2-longitude1));
    }
}