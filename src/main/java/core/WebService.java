package core;

import models.TransportStation;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

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
     * Method for retrieving the time when the first vehicle departs from a station.
     *
     * @param s TransportStation: Transport element representing station we want to query for.
     * @return Returns a Node element representing the time the first vehicle will be at station s.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node getFirstVehicleForStation(TransportStation s){
        return null;
    }

    /**
     * Method for retrieving closest TransportStation.
     *
     * @param x double: latitude.
     * @param y double: longitude.
     * @return Returns a Node element representing the closest TransportStation by x,y positions.
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
//        System.out.println("current(" + latitude1 + ", " + longitude1 + ") VS station(" + latitude2 + ", " + longitude2 + ")");
        return Math.sqrt((latitude2-latitude1)*(latitude2-latitude1) + (longitude2-longitude1)*(longitude2-longitude1));
    }
}