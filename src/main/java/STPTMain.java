import CamelComponents.CamelREST;
import CamelComponents.CamelWebService;
import core.StationsInteractor;
import core.TimeTablesInteractor;
import core.VehiclesInteractor;
import core.WebService;
import org.apache.camel.main.Main;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class STPTMain {
    public static void IterateNodeList(NodeList query_res){
        for(int i = 0; i < query_res.getLength(); i++){
            System.out.println(query_res.item(i).getNodeName() + " " +
                    query_res.item(i).getAttributes().item(0) + " '" +
                    query_res.item(i).getTextContent() + "'");
        }
    }

    public static void main(String args[]) throws Exception {
        StationsInteractor stationsInteractor = new StationsInteractor("data/statii-ratt.xml");
        VehiclesInteractor vehiclesInteractor = new VehiclesInteractor("data/vehicles.xml");
        TimeTablesInteractor timetablesInteractor = new TimeTablesInteractor("data/timetables.xml");

//        NodeList allStations = stationsInteractor.getAllStations();
//        NodeList allVehicles = vehiclesInteractor.getAllVehicles();
//        NodeList allTimetables = timetablesInteractor.getAllTimeTables();
//
//        stationsInteractor.prettyPrintNodeList(allStations);
//        vehiclesInteractor.prettyPrintNodeList(allVehicles);
//        timetablesInteractor.prettyPrintNodeList(allTimetables);

//        Node foundStation = stationsInteractor.getStation(2810);
//        stationsInteractor.prettyPrintNode(foundStation);

//        Node foundVehicle = vehiclesInteractor.getVehicle(158);
//        vehiclesInteractor.prettyPrintNode(foundVehicle);
//        WebService ws = new WebService(
//                "data/vehicles.xml",
//                "data/timetables.xml",
//                "data/statii-ratt.xml"
//        );
//
//        Node closestStation = ws.getClosestStation(45.748261, 21.219212);
//        stationsInteractor.prettyPrintNode(closestStation);
        Main main = new Main();
        main.configure().addRoutesBuilder(new CamelREST());
        main.configure().addRoutesBuilder(new CamelWebService());
        main.run(args);
    }
}