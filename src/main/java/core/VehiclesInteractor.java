package core;

import Models.Vehicle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

public class VehiclesInteractor extends Interactor {

    public VehiclesInteractor(String path_to_doc) throws JAXBException, ParserConfigurationException {
        super(path_to_doc);
    }

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

    public Node createVehicle(Vehicle v) throws XPathExpressionException {
        return this.createVehicle(v.vehicleID, v.vehicleName, v.vehicleType);
    }


    public NodeList getAllVehicles() throws XPathExpressionException {
        return xputils.QueryXPath("/vehicles-root/vehicles/vehicle");
    }

    public Node getVehicle(Integer vehicle_id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//vehicle[@id=%s]", vehicle_id)).item(0);
    }

    public Document deleteVehicle(Integer id) throws XPathExpressionException {
        Element vehicle_to_delete = (Element) xputils.QueryXPath(String.format("//vehicle[@id=%s]", id)).item(0);
        vehicle_to_delete.getParentNode().removeChild(vehicle_to_delete);

        return this.document;
    }

    public Document replaceVehicle(Integer id, Vehicle vehicle) throws XPathExpressionException {
        System.out.println("Replacing: " + vehicle.toString());
        Node node_to_replace = this.getVehicle(id);
        Node new_au = this.createVehicle(vehicle.vehicleID, vehicle.vehicleName, vehicle.vehicleType);
        node_to_replace.getParentNode().replaceChild(new_au, node_to_replace);

        return this.document;
    }
}
