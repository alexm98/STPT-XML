package parsers;

import Models.TransportStation;
import Models.Wrapper;
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

public class ParserUtils {
    public String path_to_doc;

    public ParserUtils(String path_to_doc){
        this.path_to_doc = path_to_doc;
    }

    public Document parseJAXB() throws JAXBException, ParserConfigurationException {
        File doc = new File(this.path_to_doc);
        JAXBContext jc = JAXBContext.newInstance(Wrapper.class, TransportStation.class);

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

    public void SaveDoc(Document doc, String location) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        Result output = new StreamResult(new File(location));
        Source input = new DOMSource(doc);
        transformer.transform(input, output);
    }
}
