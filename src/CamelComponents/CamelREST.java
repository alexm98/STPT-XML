package CamelComponents;

import core.StationsInteractor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import parsers.ParserUtils;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

@Component
public class CamelREST extends RouteBuilder {
    private final StationsInteractor stations_interactor;
    private final ParserUtils putils;

    public CamelREST() throws JAXBException, ParserConfigurationException, SAXException {
        this.stations_interactor = new StationsInteractor("data/statii-ratt-format.xml");
        this.putils = new ParserUtils("doc.xml");
    }

    @Override
    public void configure() throws Exception {
        restConfiguration().component("netty-http")
                .host("localhost")
                .port("9091")
                .bindingMode(RestBindingMode.auto);

//        rest("/api")
//                .get("/authors")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "getAllAuthors")
//                .endRest()
//
//                .get("/publications")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "getAllPublications")
//                .endRest()
//
//                .get("/affiliations")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "getAllAffiliations")
//                .endRest()
//
//                .get("/author/{id}")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "getAuthor(${header.id})")
//                .endRest()
//
//                .put("/author/{id}")
//                .type(Author.class)
//                .consumes("application/xml")
//                .produces("application/xml")
//                .route()
//                .to("log:mylogger?showAll=true")
//                .bean(xml_interactor, "replaceAuthor(${header.id}, ${body})")
//                .endRest()
//
//                .delete("/author/{id}")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "deleteAuthor(${header.id})")
//                .endRest()
//
//                .post("/author/{name}")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "createAuthor(${header.name})")
//                .endRest()
//
//                // affiliations
//
//                .get("/affiliation/{rid}")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "getAffiliation(${header.rid})")
//                .endRest()
//
//                .put("/affiliation/{rid}")
//                .type(Affiliation.class)
//                .produces("application/xml")
//                .consumes("application/xml")
//                .route()
//                .to("log:mylogger?showAll=true")
//                .bean(xml_interactor, "replaceAffiliation(${header.rid}, ${body})")
//                .endRest()
//
//                .delete("/affiliation/{rid}")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "deleteAffiliation(${header.rid})")
//                .endRest()
//
//                .post("/affiliation/{rid}/{institution_name}")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "createAffiliation(${header.rid}, ${header.institution_name})")
//                .endRest()
//
//                // Publications
//
//                .get("/publication?doi={doi}")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "getPublication(${header.doi})")
//                .endRest()
//
//                .put("/publication?doi={doi}")
//                .type(Article.class)
//                .produces("application/xml")
//                .consumes("application/xml")
//                .route()
//                .bean(xml_interactor, "replacePublication(${body})")
//                .endRest()
//
//                .delete("/publication?doi={doi}")
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "deletePublication(${header.doi})")
//                .endRest()
//
//                .post("/publication?doi={doi}")
//                .type(Article.class)
//                .produces("application/xml")
//                .route()
//                .bean(xml_interactor, "createPublication(${body})")
//                .endRest();
    }
};
