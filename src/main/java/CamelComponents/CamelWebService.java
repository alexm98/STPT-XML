package CamelComponents;

import core.WebService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

public class CamelWebService extends RouteBuilder {
    private final WebService webservice_methods;

    public CamelWebService() throws JAXBException, ParserConfigurationException {
        this.webservice_methods = new WebService("data/vehicles.xml", "data/timetables.xml", "data/statii-ratt.xml");
    }

    @Override
    public void configure(){
        // ToDo: modify configuration here
        // ToDo: Add routes with .from and .bean()
        restConfiguration().component("netty-http")
                .host("localhost")
                .port("9092")
                .bindingMode(RestBindingMode.auto);
    }
}
