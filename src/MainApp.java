import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.bind.*;

import org.w3c.dom.NodeList;

import bibliography.*;
import parsers.*;


public class MainApp {
    public static void XPathQueryExamples() throws JAXBException {
        File doc = new File("doc.xml");
        JAXBContext jc = JAXBContext.newInstance(Wrapper.class, Author.class, Affiliation.class,
                Articles.class, Article.class, Journal.class, Person.class);

        Marshaller marshaller = jc.createMarshaller();

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Wrapper root = (Wrapper) unmarshaller.unmarshal(doc);

        XPathUtils p = new XPathUtils(marshaller, root);

        try{
            // I am using the xpath_queries.txt file to read the XPath expressions from
            BufferedReader reader = new BufferedReader(new FileReader("xpath_queries.txt"));
            String expr = reader.readLine();

            while(expr != null){
                if(!expr.startsWith("#")) {
                    if(expr.contains("@") || expr.contains("text()")){
                        ArrayList<String> query_res = p.QueryXPathString(expr);

                        for(String el: query_res){
                            System.out.println(el);
                        }
                    }
                    else{
                        NodeList query_res = p.QueryXPath(expr);
                        for(int i = 0; i < query_res.getLength(); i++){
                            System.out.println(query_res.item(i).getNodeName() + " " +
                                    query_res.item(i).getAttributes().item(0) + " '" +
                                    query_res.item(i).getTextContent() + "'");
                        }
                    }
                }
                expr = reader.readLine();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String args[]) throws Exception {
        ParserUtils p = new ParserUtils();
//        p.parseSax("doc.xml");
        p.parseJAXB("doc.xml");
//        XPathQueryExamples();

//        Main main = new Main();
//        main.addRouteBuilder(new CamelComponents.StAXCamelRoute());
//        main.addRouteBuilder(new CamelComponents.CamelValidatorRoute());
//        main.run(args);
    }
}
