package core;

import Models.Vehicle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

/**
 * Class which holds the implementation for interacting with a vehicle object.
 *
 * A vehicle element of of the following structure in the XML:
 *
 *  {@code <vehicle id="3307">
 *      <vehicle-name>M42</vehicle-name>
 *      <vehicle-type>Bus</vehicle-type>
 *  </vehicle>}
 *
 *  Using the VehiclesInteractor class we can operate on such elements by parsing the XML document and using the
 *  XPathUtils class to query, delete, edit and add.
 */
public class VehiclesInteractor extends Interactor {

    /**
     * Constructor of the VehiclesInteractor class, which calls the parent class for creating the marshalled XML
     * document.
     * @param path_to_doc Path to the XML document which will be used by the interactor.
     * @throws JAXBException @see JAXBException
     * @throws ParserConfigurationException @see ParserConfigurationException
     */
    public VehiclesInteractor(String path_to_doc) throws JAXBException, ParserConfigurationException {
        super(path_to_doc);
    }

    /**
     * Method which is used for creating a new element of the type vehicle.
     *
     * This is achieved by using XPath for finding where to place the new vehicle element, and creating it based on the
     * passed parameters. After creation, we append the new Element to the parent.
     * @param new_id Integer: Id of the vehicle to be added. Example: 3306
     * @param vehicleName String: Name of the vehicle to be added. Example: M42
     * @param vehicleType String: Type of the vehicle to be added. Example: Bus
     * @return Returns a Node object which represents the newly added vehicle element.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node createVehicle(Integer new_id, String vehicleName, String vehicleType) throws XPathExpressionException {
        // get last vehicle ID, create a new node and add it to it's parent
        Element last_vehicle = (Element) (xputils.QueryXPath("//timetable[not(@vehicle_id <= preceding-sibling::timetable/@vehicle_id) and not(@vehicle_id <=following-sibling::timetable/@vehicle_id)]").item(0));

        if(new_id == null) {
            new_id = Integer.parseInt(last_vehicle.getAttribute("id")) + 1;
        }

        Element new_vehicle = this.document.createElement("vehicle");
        new_vehicle.setAttribute("id", new_id.toString());
        Element vehicle_name = this.document.createElement("vehicle-name");
        vehicle_name.setTextContent(vehicleName);
        Element vehicle_type = this.document.createElement("vehicle-type");
        vehicle_type.setTextContent(vehicleType);

        new_vehicle.appendChild(vehicle_name);
        new_vehicle.appendChild(vehicle_type);
        last_vehicle.getParentNode().appendChild(new_vehicle);

        return new_vehicle;
    }

    /**
     * Method for creating a new vehicle which is used by JAXB binding.
     * @param v Vehicle: Vehicle element representing the new element to be added.
     * @return Returns a Node element representing the newly added vehicle element.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node createVehicle(Vehicle v) throws XPathExpressionException {
        return this.createVehicle(v.vehicleID, v.vehicleName, v.vehicleType);
    }


    /**
     * Method for querying for all available vehicles, taken from the parent XML document.
     *
     * The querying is done by passing the following xPath expression to the XPathUtils object:
     * "/vehicles-root/vehicles/vehicle"
     *
     * @return NodeList: A list of Nodes representing all the matched elements found by the query.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public NodeList getAllVehicles() throws XPathExpressionException {
        return xputils.QueryXPath("/vehicles-root/vehicles/vehicle");
    }

    /**
     * Method for finding a vehicle based on a given id.
     *
     * The querying is done by passing the searched id in the following xPath expression, and passing the expression to
     * the XPathUtils class:
     * "//vehicle[@id=%s]"
     *
     * The vehicle whose id matches the required id will be returned.
     *
     * @param vehicle_id Integer: Searched vehicle id.
     * @return Node: If the vehicle with the requested id has been found, it will be returned.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Node getVehicle(Integer vehicle_id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//vehicle[@id=%s]", vehicle_id)).item(0);
    }

    /**
     * Method for deleting an element of type vehicle based on a given id.
     *
     * The querying to find the vehicle whose specific id is the requested one is done by passing the following xPath
     * expression to the XPathUtils object:
     * "//vehicle[@id=%s]"
     *
     * If the requested vehicle is found, it will be removed from its parent in the XML document.
     * @param id Integer: id for finding the requested vehicle.
     * @return Document: The XML document which has the requested vehicle deleted.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Document deleteVehicle(Integer id) throws XPathExpressionException {
        Element vehicle_to_delete = (Element) xputils.QueryXPath(String.format("//vehicle[@id=%s]", id)).item(0);
        vehicle_to_delete.getParentNode().removeChild(vehicle_to_delete);

        return this.document;
    }

    /**
     * Method for replacing an element of type vehicle with a new Vehicle, based on a given id.
     *
     * The querying to find the requested vehicle to be replaced will be done by using the existent
     * getVehicle(Integer id) method. If the vehicle is found, a new vehicle is created with the new requirements and
     * the parent will now replace the old vehicle with the new one.
     * @param id Integer: id for finding the requested vehicle.
     * @param vehicle Vehicle: Replacement for the old vehicle element.
     * @return Document: The XML document which has the requested vehicle replaced.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public Document replaceVehicle(Integer id, Vehicle vehicle) throws XPathExpressionException {
        Node node_to_replace = this.getVehicle(id);
        Node new_au = this.createVehicle(vehicle.vehicleID, vehicle.vehicleName, vehicle.vehicleType);
        node_to_replace.getParentNode().replaceChild(new_au, node_to_replace);

        return this.document;
    }
}