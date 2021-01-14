package CamelComponents;

import core.XMLInteractor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

@Component
public class CamelXSLT extends RouteBuilder {
    private final XMLInteractor xml_interactor;

    public CamelXSLT() throws JAXBException, ParserConfigurationException, SAXException {
        this.xml_interactor = new XMLInteractor("doc.xml");
    }

    @Override
    public void configure() throws Exception {
        restConfiguration().component("netty-http")
                .host("localhost")
                .port("9091")
                .bindingMode(RestBindingMode.auto);

        rest("/xslt")
                .get("/authors")
                .produces("application/xml")
                .route()
                .bean(this.xml_interactor, "getDocument")
                .setHeader(Exchange.CONTENT_TYPE, simple("text/html"))
                .to("xslt:file:xslt/authors-list.xsl")
                .endRest()

                .get("/affiliations")
                .produces("application/xml")
                .route()
                .bean(this.xml_interactor, "getDocument")
                .setHeader(Exchange.CONTENT_TYPE, simple("text/html"))
                .to("xslt:file:xslt/affiliations-list.xsl")
                .endRest()

                .get("/publications")
                .produces("application/xml")
                .route()
                .bean(this.xml_interactor, "getDocument")
                .setHeader(Exchange.CONTENT_TYPE, simple("text/html"))
                .to("xslt:file:xslt/publications-table.xsl")
                .endRest()

                .get("/authors-pubs")
                .produces("application/xml")
                .route()
                .bean(this.xml_interactor, "getDocument")
                .setHeader(Exchange.CONTENT_TYPE, simple("text/html"))
                .to("xslt:file:xslt/authors-publications.xsl")
                .endRest()

                .get("/affiliation-pubs")
                .produces("application/xml")
                .route()
                .bean(this.xml_interactor, "getDocument")
                .setHeader(Exchange.CONTENT_TYPE, simple("text/html"))
                .to("xslt:file:xslt/affiliations-publications.xsl")
                .endRest();
    }
};
