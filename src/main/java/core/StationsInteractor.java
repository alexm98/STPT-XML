package core;

import Models.TransportStation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
        return xputils.QueryXPath("/transport-stations-root/transport-stations/transport-station");
    }

    public Node getStation(Integer station_id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//transport-station[@id=%s]", station_id)).item(0);
    }

    public Node createStation(Integer new_id, int lineID, String lineName, int stationID, String rawStationName,
                              String friendlyStationName, String shortStationName, String junctionName,
                              double x, double y, Boolean is_invalid, String verif,
                              String verif_date, String gmaps_links, String info_comm) throws XPathExpressionException{

        Element last_station = (Element) (xputils.QueryXPath("//transport-station[not(@id <= preceding-sibling::transport-station/@id) and not(@id <=following-sibling::transport-station/@id)]").item(0));
//        Element last_station = (Element) (xputils.QueryXPath("//transport-station/last()[@id]").item(0));

        if(new_id == null) {
            new_id = Integer.parseInt(last_station.getAttribute("id")) + 1;
        }

        Element new_station = this.document.createElement("transport-station");
        new_station.setAttribute("id", String.valueOf(new_id));

        Element line_id = this.document.createElement("line-id");
        line_id.setTextContent(String.valueOf(lineID));
        Element line_name = this.document.createElement("line-name");
        line_name.setTextContent(lineName);
        Element raw_station_name = this.document.createElement("raw-station-name");
        raw_station_name.setTextContent(rawStationName);
        Element friendly_station_name = this.document.createElement("friendly-station-name");
        friendly_station_name.setTextContent(friendlyStationName);
        Element short_station_name = this.document.createElement("short-station-name");
        short_station_name.setTextContent(shortStationName);
        Element junciton_name = this.document.createElement("junciton-name");
        junciton_name.setTextContent(junctionName);
        Element lat = this.document.createElement("lat");
        lat.setTextContent(String.valueOf(x));
        Element longitude = this.document.createElement("long");
        longitude.setTextContent(String.valueOf(y));
        Element invalid = this.document.createElement("invalid");
        invalid.setTextContent(String.valueOf(is_invalid));
        Element verified = this.document.createElement("verified");
        verified.setTextContent(verif);
        Element verification_date = this.document.createElement("verification-date");
        verification_date.setTextContent(verif_date);
        Element gmaps_link = this.document.createElement("gmaps-link");
        gmaps_link.setTextContent(gmaps_links);
        Element info_comments = this.document.createElement("info-comments");
        info_comments.setTextContent(info_comm);

        new_station.appendChild(line_id);
        new_station.appendChild(line_name);
        new_station.appendChild(raw_station_name);
        new_station.appendChild(friendly_station_name);
        new_station.appendChild(short_station_name);
        new_station.appendChild(junciton_name);
        new_station.appendChild(lat);
        new_station.appendChild(longitude);
        new_station.appendChild(invalid);
        new_station.appendChild(verified);
        new_station.appendChild(verification_date);
        new_station.appendChild(gmaps_link);
        new_station.appendChild(info_comments);

        last_station.getParentNode().appendChild(new_station);

        return new_station;
    }

    public Node createStation(TransportStation t) throws XPathExpressionException {
        return this.createStation(null, t.lineID, t.lineName, t.stationID, t.rawStationName, t.friendlyStationName,
                t.shortStationName, t.junctionName, t.lat, t.longitude, t.is_invalid, t.verified,
                t.verification_date, t.gmaps_links, t.info_comments);
    }

    public Document replaceStation(Integer id, TransportStation t) throws XPathExpressionException {
        Node node_to_replace = this.getStation(id);
        Node new_au = this.createStation(t);
        node_to_replace.getParentNode().replaceChild(new_au, node_to_replace);

        return this.document;
    }

    public Document deleteStation(Integer id) throws XPathExpressionException {
        Element station_to_delete = (Element) getStation(id);
        station_to_delete.getParentNode().removeChild(station_to_delete);

        return this.document;
    }
}
