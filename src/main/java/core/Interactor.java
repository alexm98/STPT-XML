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

public class Interactor {
    protected Document document;
    protected XPathUtils xputils;
    protected ParserUtils putils;

    public Interactor(String path_to_doc) throws JAXBException, ParserConfigurationException {
        this.putils = new ParserUtils(path_to_doc);
        this.document = this.putils.parseJAXB();
        this.xputils = new XPathUtils(this.document);
    }

    public Document getDocument(){
        return this.document;
    }

    public void SaveDocument(String location) throws TransformerException {
        try{
            this.putils.SaveDoc(this.document, location);
        }
        catch (TransformerException e){
            System.out.println(e.getMessage());
        }
    }

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

    public void prettyPrintNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element el = (Element) node;
            System.out.println(el.getNodeName() + " " +
                    el.getAttributes().item(0) + " " +
                    el.getTextContent());
        }
    }
}
