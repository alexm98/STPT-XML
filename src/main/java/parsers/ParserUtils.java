package parsers;

import Models.*;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Class which implements basic parsing methods over an XML document.
 */
public class ParserUtils {
    public String path_to_doc;

    /**
     * Constructor of the ParserUtil class.
     * @param path_to_doc Location of the XML document to be used.
     */
    public ParserUtils(String path_to_doc){
        this.path_to_doc = path_to_doc;
    }

    /**
     * Method which parses an XML document by using JAXB.
     *
     * This is achieved by specifying which classes are to be taken into consideration for JAXB binding, then
     * unmarshalling the XML document into the classes and returning the marshalled document back.
     * @return Marshalled XML document.
     * @throws JAXBException @see JAXBException
     * @throws ParserConfigurationException @see ParserConfigurationException
     */
    public Document parseJAXB() throws JAXBException, ParserConfigurationException {
        File doc = new File(this.path_to_doc);
        JAXBContext jc = JAXBContext.newInstance(
                TimetablesWrapper.class,
                StationsWrapper.class,
                VehiclesWrapper.class,
                TransportStation.class,
                Vehicle.class,
                Direction.class,
                Arrival.class,
                TimeTable.class,
                Time.class);

//        SchemaFactory f = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        Schema schema = f.newSchema(new File("schema.xsd"));

        Unmarshaller unmarshaller = jc.createUnmarshaller();
//        unmarshaller.setSchema(schema);
        Object root = (Object) unmarshaller.unmarshal(doc);

        // Marshal into the returned Document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.newDocument();
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(root, document);

        return document;
    }

    /**
     * Method which, given a document and a location, saves the document at the specific location.
     * @param doc Document to be saved.
     * @param location Location where the document will be saved.
     * @throws TransformerException @see TransformerException
     */
    public void SaveDoc(Document doc, String location) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        Result output = new StreamResult(new File(location));
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
    }
}