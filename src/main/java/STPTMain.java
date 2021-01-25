import CamelComponents.CamelREST;
import core.StationsInteractor;
import core.TimeTablesInteractor;
import core.VehiclesInteractor;
import org.apache.camel.main.Main;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import parsers.ParserUtils;

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

        NodeList allStations = stationsInteractor.getAllStations();
        NodeList allVehicles = vehiclesInteractor.getAllVehicles();
        NodeList allTimetables = timetablesInteractor.getAllTimeTables();

        stationsInteractor.prettyPrintNodeList(allStations);
        vehiclesInteractor.prettyPrintNodeList(allVehicles);
        timetablesInteractor.prettyPrintNodeList(allTimetables);

//        System.out.println(s.getStation(2810));
//        System.out.println(v.getVehicle(158));

//        Main main = new Main();
//        main.configure().addRoutesBuilder(new CamelREST());
//        main.run(args);
    }
}
