package CamelComponents;

import org.apache.camel.builder.RouteBuilder;

import core.XMLInteractor;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

public class CamelRoutes extends RouteBuilder {
    private final XMLInteractor xml_interactor;

    public CamelRoutes() throws JAXBException, ParserConfigurationException, SAXException {
        this.xml_interactor = new XMLInteractor("doc.xml");
    }

    @Override
    public void configure() throws Exception {
        from("file:camel/input/pubs/")
                .to("validator:file:schema.xsd")
                .bean(xml_interactor, "getAllPublications")
                .to("file:camel/output/publications.xml")
                .end();

        from("file:camel/input/authors/")
                .to("validator:file:schema.xsd")
                .bean(xml_interactor, "getAllAuthors")
                .to("file:camel/output/authors")
                .end();

        from("file:camel/input/author-add/")
                .to("validator:file:schema.xsd")
                .bean(xml_interactor, "createAuthor(Alexandru Munteanu)")
                .bean(xml_interactor, "createAffiliation(19, West University of Timisoara)")
                .bean(xml_interactor, "getDocument")
                .to("file:camel/output/author/")
                .end();

        from("file:camel/input/affils/")
                .to("validator:file:schema.xsd")
                .bean(xml_interactor, "getAllAffiliations")
                .to("file:camel/output/authors")
                .end();

        from("file:camel/input/author/")
                .to("validator:file:schema.xsd")
                .bean(xml_interactor, "getAuthor(3)")
                .to("file:camel/output/author_3")
                .end();

        from("file:camel/input/affiliation/")
                .to("validator:file:schema.xsd")
                .bean(xml_interactor, "getAffiliation(8)")
                .to("file:camel/output/affiliation_8")
                .end();

        from("file:camel/input/publication/")
                .to("validator:file:schema.xsd")
                .bean(xml_interactor, "getPublication(10.4018/IJGHPC.2016010101)")
                .to("file:camel/output/publication")
                .end();
    }
}
