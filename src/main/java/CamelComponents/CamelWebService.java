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
        from("netty-http:http://0.0.0.0:8080/{station}")
                .bean(webservice_methods, "getLastVehicleForStation(${header.station})")
                .setHeader(Exchange.CONTENT_TYPE, simple("application/xml"));
    }
}
