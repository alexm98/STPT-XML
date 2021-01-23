package core;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import parsers.ParserUtils;
import parsers.XPathUtils;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

public class VehiclesInteractor {
    private Document document;
    private XPathUtils xputils;
    private ParserUtils putils;

    public VehiclesInteractor(String path_to_doc) throws
            ParserConfigurationException,
            JAXBException {
        this.putils = new ParserUtils(path_to_doc);
        this.document = this.putils.parseJAXB();
        this.xputils = new XPathUtils(this.document);
    }

    public NodeList getAllVehicles() throws XPathExpressionException {
        return xputils.QueryXPath("/root/vehicles");
    }

    public NodeList getVehicle(Integer vehicle_id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//vehicle[@id=%s]", vehicle_id));
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
}
