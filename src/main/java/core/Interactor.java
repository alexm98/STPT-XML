package core;

import org.w3c.dom.Document;
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
}
