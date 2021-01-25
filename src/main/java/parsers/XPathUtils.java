package parsers;

import Models.StationsWrapper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;

/* This class converts a JAXB content tree into a DOM tree
 so I can use XPath queries more appropriately
*/

/**
 * Class which implements the XPath operations needed for the application.
 */
public class XPathUtils {
    public Document doc;

    /**
     * Constructor of the XPathUtils clas.
     *
     * @param doc XML document, used for querying.
     */
    public XPathUtils(Document doc){
        this.doc = doc;
    }


    public XPathUtils(Marshaller marshaller, StationsWrapper data) {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder builder = fac.newDocumentBuilder();
            this.doc = builder.newDocument();
            marshaller.marshal(data, this.doc);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method which, given a query in the form of a String object, generates a ArrayList<String> of responses using
     * XPath.
     *
     * @param query Query which will be used for generating the ArrayList<String> results.
     * @return ArrayList<String> Results of the given query.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public ArrayList<String> QueryXPathString(String query) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPathExpression xpath = factory.newXPath().compile(query);

        ArrayList<String> results = new ArrayList<String>();

        try{
            NodeList node_list = (NodeList) xpath.evaluate(
                    doc, XPathConstants.NODESET);

            for(int i = 0; i < node_list.getLength(); i++){
                results.add(node_list.item(i).getNodeName() + " "
                        + node_list.item(i).getNodeValue() + " "
                        + node_list.item(i).getAttributes().item(0)
                        + node_list.item(i).getTextContent());
            }

            return results;
        }
        catch (XPathExpressionException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Method which, given a query in the form of a String object, generates a NodeList of responses using XPath.
     * @param query Query which will be used for generating the ArrayList<String> results.
     * @return NodeList Results of the given query.
     * @throws XPathExpressionException @see XPathExpressionException
     */
    public NodeList QueryXPath(String query) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPathExpression xpath = factory.newXPath().compile(query);

        ArrayList<String> results = new ArrayList<String>();

        try{
            NodeList node_list = (NodeList) xpath.evaluate(
                    doc, XPathConstants.NODESET);

            return node_list;
        }
        catch (XPathExpressionException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void printNodes(NodeList node_list){
    }
}