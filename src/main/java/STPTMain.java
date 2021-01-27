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
    public static void main(String args[]) throws Exception {
        WebService ws = new WebService(
                "data/vehicles.xml",
                "data/timetables.xml",
                "data/statii-ratt.xml"
        );
        //ws.getLastDepartureVehicle("5841");
        ws.getLastArrivalVehicle("5841");
        Main main = new Main();
        main.configure().addRoutesBuilder(new CamelREST());
        main.configure().addRoutesBuilder(new CamelWebService());
        main.run(args);
    }
}