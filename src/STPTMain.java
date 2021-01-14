import CamelComponents.CamelREST;
import core.StationsInteractor;
import org.apache.camel.main.Main;
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
//        ParserUtils putils = new ParserUtils("data/statii-ratt-format.xml");
//        Document doc = putils.parseJAXB();
        StationsInteractor s = new StationsInteractor("data/statii-ratt-format.xml");
        //IterateNodeList(s.getAllStations());
        System.out.println(s.getStation(4680));


        Main main = new Main();
        main.configure().addRoutesBuilder(new CamelREST());
        main.run(args);
    }
}
