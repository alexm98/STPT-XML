package CamelComponents;

import core.WebService;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

public class CamelWebService extends RouteBuilder {
    private final WebService webservice_methods;

    public CamelWebService() throws JAXBException, ParserConfigurationException {
        this.webservice_methods = new WebService("data/vehicles.xml", "data/timetables.xml", "data/statii-ratt.xml");
    }

    @Override
    public void configure(){
        from("netty-http:http://0.0.0.0:8080/closestStation/?latitude={latitude}&longitude={longitude}")
                .setHeader("Access-Control-Allow-Origin",  constant("http://localhost:8000"))
                .bean(webservice_methods, "getClosestStation(${header.latitude},${header.longitude})")
                .setHeader(Exchange.CONTENT_TYPE, simple("application/xml"));

        from("netty-http:http://0.0.0.0:8080/allArrivals/{station_id}")
                .setHeader("Access-Control-Allow-Origin",  constant("http://localhost:8000"))
                .bean(webservice_methods, "getAllArrivals(${header.station_id})")
                .setHeader(Exchange.CONTENT_TYPE, simple("application/xml"));

        from("netty-http:http://0.0.0.0:8080/allDepartures/{station_id}")
                .setHeader("Access-Control-Allow-Origin",  constant("http://localhost:8000"))
                .bean(webservice_methods, "getAllDepartures(${header.station_id})")
                .setHeader(Exchange.CONTENT_TYPE, simple("application/xml"));

        from("netty-http:http://0.0.0.0:8080/lastDeparture/{station_id}")
                .setHeader("Access-Control-Allow-Origin",  constant("http://localhost:8000"))
                .bean(webservice_methods, "getLastDepartureVehicle(${header.station_id})")
                .setHeader(Exchange.CONTENT_TYPE, simple("application/xml"));

        from("netty-http:http://0.0.0.0:8080/lastArrival/{station_id}")
                .setHeader("Access-Control-Allow-Origin",  constant("http://localhost:8000"))
                .bean(webservice_methods, "getLastArrivalVehicle(${header.station_id})")
                .setHeader(Exchange.CONTENT_TYPE, simple("application/xml"));
    }
}