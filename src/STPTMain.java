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
        ParserUtils putils = new ParserUtils("data/statii-ratt.xml");
        Document doc = putils.parseJAXB();
//        Main main = new Main();
//        main.configure().addRoutesBuilder(new CamelRoutes());
//        main.configure().addRoutesBuilder(new CamelREST());
//        main.configure().addRoutesBuilder(new CamelXSLT());
//        main.run(args);
    }
}
