package Models;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "TransportStation")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransportStation {
    @XmlElement(name = "LineID")
    public int lineID;
    @XmlAttribute(name = "id")
    public int stationID;
    @XmlElement(name = "LineName")
    public String lineName;
    @XmlElement(name="RawStationName")
    public String rawStationName;
    @XmlElement(name = "FriendlyStationName")
    public String friendlyStationName;
    @XmlElement(name = "ShortStationName")
    public String shortStationName;
    @XmlElement(name = "JunctionName")
    public String junctionName;
    @XmlElement(name = "Lat")
    public double lat;
    @XmlElement(name = "Long")
    public double longitude;
    @XmlElement(name = "Invalid")
    public Boolean is_invalid;
    @XmlElement(name = "Verified")
    public String verified;
    @XmlElement(name = "VerificationDate")
    public String verification_date;
    @XmlElement(name = "GMapsLink")
    public String gmaps_links;
    @XmlElement(name = "InfoComments")
    public String info_comments;

    public TransportStation(){
    }

    public TransportStation(int station_id){
        this.stationID = station_id;
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
