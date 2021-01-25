package core;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

public class StationsInteractor extends Interactor {

    public StationsInteractor(String path_to_doc) throws
            ParserConfigurationException,
            JAXBException {
        super(path_to_doc);
    }

    public NodeList getAllStations() throws XPathExpressionException {
        return xputils.QueryXPath("/transport-stations-root/TransportStations/TransportStation");
    }

    public Node getStation(Integer station_id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//TransportStation[@id=%s]", station_id)).item(0);
    }
}
