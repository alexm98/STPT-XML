package core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import parsers.ParserUtils;
import parsers.XPathUtils;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Class which represents the base for the interactors.
 *
 * Through this, one can access the document and pretty print methods.
 */
public class Interactor {
    protected Document document;
    protected XPathUtils xputils;
    protected ParserUtils putils;

    /**
     * Constructor of the Interactor class.
     * @param path_to_doc Path to the XML document to be used.
     * @throws JAXBException @see JAXBException
     * @throws ParserConfigurationException @see ParserConfigurationException
     */
    public Interactor(String path_to_doc) throws JAXBException, ParserConfigurationException {
        this.putils = new ParserUtils(path_to_doc);
        this.document = this.putils.parseJAXB();
        this.xputils = new XPathUtils(this.document);
    }

    /**
     * Method which returns the parsed XML document.
     * @return Return the parsed XML document.
     */
    public Document getDocument(){
        return this.document;
    }

    /**
     * Method which saves the XML document.
     * @param location Location of the updated document.
     * @throws TransformerException @see TransformerException
     */
    public void SaveDocument(String location) throws TransformerException {
        try{
            this.putils.SaveDoc(this.document, location);
        }
        catch (TransformerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to pretty print the elements of a NodeList argument.
     * @param nodeList A list of Node elements.
     */
    public void prettyPrintNodeList(NodeList nodeList) {
        int length = nodeList.getLength();

        if (length == 0) {
            System.out.println("There are no items in the requested node list.");
        }

        for (int i = 0; i < length; i++) {
            Node currentNode = nodeList.item(i);
            prettyPrintNode(currentNode);
        }

        System.out.println();
    }

    /**
     * Method to pretty print a Node element.
     * @param node Node element to be printed.
     */
    public void prettyPrintNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element el = (Element) node;
            System.out.println(el.getNodeName() + " " +
                    el.getAttributes().item(0) + " " +
                    el.getTextContent());
        }
    }
}