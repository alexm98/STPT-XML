package parsers;

import bibliography.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class ParserUtils {
    public ParserUtils(){

    }

    public static void parseSax(String path_to_file) throws ParserConfigurationException, SAXException {
        BibliographyHandler bh = new BibliographyHandler();
        File xml_document = new File(path_to_file);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        try{
            parser.parse(xml_document, bh);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        for(Article a: bh.searchByAuthorName("Dana Petcu")){
            System.out.println(a);
        }
    }

    public static Document parseJAXB(String path_to_file) throws JAXBException, SAXException, ParserConfigurationException {
        File doc = new File(path_to_file);
        JAXBContext jc = JAXBContext.newInstance(Wrapper.class, Author.class, Affiliation.class,
                Articles.class, Article.class, Journal.class, Person.class);

        SchemaFactory f = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = f.newSchema(new File("schema.xsd"));

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setSchema(schema);
        Object root = (Object) unmarshaller.unmarshal(doc);

        // Marshal into the returned Document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(root, document);

        return document;
    }

    public void SaveDoc(Document doc, String location) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        Result output = new StreamResult(new File(location));
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
    }
}
