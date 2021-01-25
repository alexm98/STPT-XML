import CamelComponents.CamelREST;
import core.StationsInteractor;
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
        StationsInteractor s = new StationsInteractor("data/statii-ratt.xml");
        VehiclesInteractor v = new VehiclesInteractor("data/vehicles.xml");

        NodeList allStations = s.getAllStations();
        NodeList allVehicles = v.getAllVehicles();

        s.prettyPrintNodeList(allStations);
        v.prettyPrintNodeList(allVehicles);

        System.out.println(s.getStation(2810));
        System.out.println(v.getVehicle(158));

//        Main main = new Main();
//        main.configure().addRoutesBuilder(new CamelREST());
//        main.run(args);
    }
}
