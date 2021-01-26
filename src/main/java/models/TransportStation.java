package models;

import javax.xml.bind.annotation.*;

/**
 * Class which holds the implementation for JAXB binding for a transport station XML element.
 *
 * A transport station element has the following structure in the XML document:
 *
 *  {@code
 *  <transport-station id="3583">
 *       <line-id>2406</line-id>
 *       <line-name>Tv9b</line-name>
 *       <raw-station-name>Bv Sudului_2</raw-station-name>
 *       <friendly-station-name>Bulevardul Sudului / Hotel Lido (AEM)</friendly-station-name>
 *       <short-station-name>Sudului</short-station-name>
 *       <junciton-name>Sudului</junciton-name>
 *       <lat>45.737211</lat>
 *       <long>21.250093</long>
 *       <invalid>0</invalid>
 *       <verified>dup script</verified>
 *       <verification-date>11.12.16.</verification-date>
 *       <gmaps-link>http://maps.google.com/maps?q=Bulevardul%20Sudului%20/%20Hotel%20Lido@45.737211,21.250093</gmaps-link>
 *       <info-comments>0</info-comments>
 * </transport-station>}
 *
 *  Using the StationsInteractor class we can perform the following operations such as delete, add, edit and query.
 */
@XmlRootElement(name = "transport-station")
@XmlAccessorType(XmlAccessType.FIELD)
public class TransportStation {
    @XmlElement(name = "line-id")
    public int lineID;
    @XmlAttribute(name = "id")
    public int stationID;
    @XmlElement(name = "line-name")
    public String lineName;
    @XmlElement(name="raw-station-name")
    public String rawStationName;
    @XmlElement(name = "friendly-station-name")
    public String friendlyStationName;
    @XmlElement(name = "short-station-name")
    public String shortStationName;
    @XmlElement(name = "junction-name")
    public String junctionName;
    @XmlElement(name = "lat")
    public double lat;
    @XmlElement(name = "long")
    public double longitude;
    @XmlElement(name = "invalid")
    public Boolean is_invalid;
    @XmlElement(name = "verified")
    public String verified;
    @XmlElement(name = "verification-date")
    public String verification_date;
    @XmlElement(name = "gmaps-link")
    public String gmaps_links;
    @XmlElement(name = "info-comments")
    public String info_comments;

    public TransportStation(){
    }

    /**
     * Constructor for the TransportStation class, using only a station id for creation.
     * @param station_id Id of the station.
     */
    public TransportStation(int station_id){
        this.stationID = station_id;
    }

    /**
     * Constructor for the TransportStation object.
     *
     * All the necessary parameters for constructing a transport station element are set here.
     *
     * @param lineID int: Id of the line for the transport station. Example: 1266.
     * @param lineName String: Name of the line. Example: Tv4.
     * @param stationID int: id of the station.
     * @param rawStationName String: Raw name for the station. Example: P-ta Crucii_2.
     * @param friendlyStationName String: Friendlier version of the raw station name.
     *                            Example: Piata Crucii (Torontalului)
     * @param shortStationName String: Shorter version for the station name. Example: P-ta Crucii.
     * @param junctionName String: Name of the junction. Example: P-ta Crucii.
     * @param lat double: Latitude of the station location.
     * @param longitude double: Longitude of the station location.
     * @param is_invalid Boolean: States whether the station is still in use.
     * @param verified String: Method of the station is verified.
     * @param verification_date String: Date of the last verification.
     * @param gmaps_links String: Link for google maps location.
     * @param info_comments String: More info.
     */
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

    /**
     * Override of string form for a TransportStation object.
     *
     * @return Pretty printed format of a vehicle instance.
     */
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