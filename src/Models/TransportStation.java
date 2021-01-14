package Models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="TransportStation")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransportStation {
    @XmlElement
    public int lineID;
    @XmlElement
    public String lineName;
    @XmlElement
    public int stationID;
    @XmlElement
    public String rawStationName;
    @XmlElement
    public String friendlyStationName;
    @XmlElement
    public String shortStationName;
    @XmlElement
    public String junctionName;
    @XmlElement
    public double lat;
    @XmlElement
    public double longitude;
    @XmlElement
    public Boolean is_invalid;
    @XmlElement
    public String verified;
    @XmlElement
    public String verification_date;
    @XmlElement
    public String gmaps_links;
    @XmlElement
    public String info_comments;

    public TransportStation(){
    }

    public TransportStation(int lineID, String lineName, int stationID, String rawStationName,
                            String friendlyStationName, String shortStationName, String junctionName,
                            double lat, double longitude, Boolean is_invalid, String verified,
                            String verification_date, String gmaps_links, String info_comments) {
        this.lineID = lineID;
        this.lineName = lineName;
        this.stationID = stationID;
        this.rawStationName = rawStationName;
        this.friendlyStationName = friendlyStationName;
        this.shortStationName = shortStationName;
        this.junctionName = junctionName;
        this.lat = lat;
        this.longitude = longitude;
        this.is_invalid = is_invalid;
        this.verified = verified;
        this.verification_date = verification_date;
        this.gmaps_links = gmaps_links;
        this.info_comments = info_comments;
    }

    @Override
    public String toString() {
        return "TransportStation{" +
                "lineID=" + lineID +
                ", lineName='" + lineName + '\'' +
                ", stationID=" + stationID +
                ", rawStationName='" + rawStationName + '\'' +
                ", friendlyStationName='" + friendlyStationName + '\'' +
                ", shortStationName='" + shortStationName + '\'' +
                ", junctionName='" + junctionName + '\'' +
                ", lat=" + lat +
                ", longitude=" + longitude +
                ", is_invalid=" + is_invalid +
                ", verified='" + verified + '\'' +
                ", verification_date='" + verification_date + '\'' +
                ", gmaps_links='" + gmaps_links + '\'' +
                ", info_comments='" + info_comments + '\'' +
                '}';
    }
}
