package core;

import bibliography.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import parsers.ParserUtils;
import parsers.XPathUtils;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;

public class XMLInteractor {
    private Document document;
    private XPathUtils xputils;
    private ParserUtils putils;

    public XMLInteractor(String path_to_doc) throws ParserConfigurationException, JAXBException, SAXException {
        this.putils = new ParserUtils();
        this.document = this.putils.parseJAXB(path_to_doc);
        this.xputils = new XPathUtils(this.document);
    }

    public NodeList getAllAuthors() throws XPathExpressionException {
        return xputils.QueryXPath("/root/authors");
    }

    public Node getAuthor(Integer id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//author[@id=%s]", id)).item(0);
    }

    public Document deleteAuthor(Integer id) throws XPathExpressionException {
        Element author_to_delete = (Element) xputils.QueryXPath(String.format("//author[@id=%s]", id)).item(0);
        author_to_delete.getParentNode().removeChild(author_to_delete);

        return this.document;
    }

    public Document replaceAuthor(Integer id, Node author) throws XPathExpressionException {
        Node node_to_replace = this.getAuthor(id);
        node_to_replace.getParentNode().replaceChild(author, node_to_replace);

        return this.document;
    }

    public Document replaceAuthor(Integer id, Author author) throws XPathExpressionException {
        Node node_to_replace = this.getAuthor(id);
        Node new_au = this.createAuthor(author.id, author.authorName);
        node_to_replace.getParentNode().replaceChild(new_au, node_to_replace);

        return this.document;
    }

    public Node createAuthor(Integer new_id, String author_name) throws XPathExpressionException {
        // get last author ID, create a new node and add it to it's parent
        Element last_author = (Element) (xputils.QueryXPath("/root/authors/author[not(@id <= preceding-sibling::author/@id) and not(@id <=following-sibling::author/@id)]").item(0));

        if(new_id == null) {
            new_id = Integer.parseInt(last_author.getAttribute("id")) + 1;
        }

        Element new_author = this.document.createElement("author");
        new_author.setTextContent(author_name);
        new_author.setAttribute("id", new_id.toString());
        last_author.getParentNode().appendChild(new_author);

        return new_author;
    }

    public Node createAuthor(String author_name) throws XPathExpressionException {
        return this.createAuthor(null, author_name);
    }

    public NodeList getAllPublications() throws XPathExpressionException {
        return xputils.QueryXPath("/root/articles");
    }

    public Node getPublication(String doi) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//article[@doi='%s']", doi)).item(0);
    }

    public Document deletePublication(String doi) throws XPathExpressionException {
        Element publication_to_delete = (Element) xputils.QueryXPath(String.format("//article[@doi='%s']", doi)).item(0);
        publication_to_delete.getParentNode().removeChild(publication_to_delete);

        return this.document;
    }

    public Document replacePublication(String doi, Node publication) throws XPathExpressionException {
        Node node_to_replace = this.getPublication(doi);
        node_to_replace.getParentNode().replaceChild(publication, node_to_replace);

        return this.document;
    }

    public Document replacePublication(Article publication) throws XPathExpressionException {
        Node node_to_replace = this.getPublication(publication.doi);
        Node new_pub = this.createPublication(publication);
        node_to_replace.getParentNode().replaceChild(new_pub, node_to_replace);

        return this.document;
    }

    public Node createPublication(String doi, ArrayList<Author> authors, String title, Journal journal, Integer pub_year) throws XPathExpressionException {
        Element publications = (Element) (xputils.QueryXPath("/root/articles").item(0));

        Element new_publication = this.document.createElement("article");
        new_publication.setAttribute("doi", doi);
        Element new_authors = this.document.createElement("authors");

        for(Author au: authors){
           Element new_author = this.document.createElement("person");
           new_author.setAttribute("id", au.id.toString());
           new_author.setTextContent(au.authorName);
           new_authors.appendChild(new_author);
        }

        Element new_journal = this.document.createElement("journal");
        new_journal.setAttribute("issn", journal.issn);
        new_journal.setTextContent(journal.journalName);

        Element pubyear = this.document.createElement("publicaitonYear");
        pubyear.setTextContent(pub_year.toString());

        new_publication.appendChild(new_authors);
        new_publication.appendChild(new_journal);
        new_publication.appendChild(pubyear);
        publications.appendChild(new_publication);

        return new_publication;
    }

    public Node createPublication(Article new_pub) throws XPathExpressionException {
        ArrayList<Author> authors = new ArrayList<>();

        for(Person p: new_pub.authors){
            String au_name = this.xputils.QueryXPath(String.format("//author[@id=%s]/text()", p.id)).item(0).getTextContent();
            authors.add(new Author(au_name,p.id));
        }

        return this.createPublication(new_pub.doi, authors, new_pub.title, new_pub.journal, new_pub.publicationYear);
    }

    public NodeList getAllAffiliations() throws XPathExpressionException {
        return xputils.QueryXPath("/root/affiliations");
    }

    public Node getAffiliation(int researcher_id) throws XPathExpressionException {
        return xputils.QueryXPath(String.format("//affiliation[@rid=%s]", researcher_id)).item(0);
    }

    public Document deleteAffiliation(int researcher_id) throws XPathExpressionException {
        Element affiliation_to_delete = (Element) xputils.QueryXPath(String.format("//affiliation[@rid=%s]", researcher_id)).item(0);
        affiliation_to_delete.getParentNode().removeChild(affiliation_to_delete);

        return this.document;
    }

    public void replaceAffiliation(Integer rid, Node affiliation) throws XPathExpressionException {
        Node node_to_replace = this.getAffiliation(rid);
        node_to_replace.getParentNode().replaceChild(affiliation, node_to_replace);
    }

    public Document replaceAffiliation(Integer rid, Affiliation affiliation) throws XPathExpressionException {
        Node node_to_replace = this.getAffiliation(rid);
        Node new_affiliation = createAffiliation(affiliation.researcher_id, affiliation.institution);
        node_to_replace.getParentNode().replaceChild(new_affiliation, node_to_replace);

        return this.document;
    }

    public Node createAffiliation(Integer researcher_id, String institution_name) throws XPathExpressionException {
        Element publications = (Element) (xputils.QueryXPath("/root/affiliations").item(0));

        Element new_affiliation = document.createElement("affiliation");
        new_affiliation.setAttribute("rid", researcher_id.toString());
        new_affiliation.setTextContent(institution_name);
        publications.appendChild(new_affiliation);

        return new_affiliation;
    }

    public Document getDocument(){
        return this.document;
    }

    public void SaveDocument(String location) throws TransformerException {
        this.putils.SaveDoc(this.document, location);
    }
}
