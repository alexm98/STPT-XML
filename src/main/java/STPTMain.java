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
        Main main = new Main();
        main.configure().addRoutesBuilder(new CamelREST());
        main.configure().addRoutesBuilder(new CamelWebService());
        main.run(args);
    }
}