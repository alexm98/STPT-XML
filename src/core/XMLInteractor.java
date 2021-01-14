package core;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import parsers.ParserUtils;
import parsers.XPathUtils;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

public class XMLInteractor {
    private Document document;
    private XPathUtils xputils;
    private ParserUtils putils;

    public XMLInteractor(String path_to_doc) throws ParserConfigurationException, JAXBException, SAXException {
        this.putils = new ParserUtils(path_to_doc);
        this.document = this.putils.parseJAXB();
        this.xputils = new XPathUtils(this.document);
    }

    public NodeList getAllAuthors() throws XPathExpressionException {
        return xputils.QueryXPath("/root/authors");
    }

    public Document getDocument(){
        return this.document;
    }

    public void SaveDocument(String location) throws TransformerException {
        this.putils.SaveDoc(this.document, location);
    }
}
