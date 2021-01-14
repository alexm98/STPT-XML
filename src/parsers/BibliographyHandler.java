package parsers;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import bibliography.*;

import java.util.ArrayList;
import java.util.HashMap;


public class BibliographyHandler extends DefaultHandler {
    public ArrayList<Article> articles = new ArrayList<>();
    public ArrayList<Author> authors = new ArrayList<>();
    public HashMap<Integer, String> affiliations = new HashMap<>();

    private Author current_author = null;
    private Article current_article = null;
    private Journal current_journal = null;

    private StringBuilder contents = new StringBuilder();

    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startPrefixMapping(String s, String s1) throws SAXException {

    }

    @Override
    public void endPrefixMapping(String s) throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName){
            case "person":
                Person p = new Person();
                p.id = Integer.parseInt(attributes.getValue(0));
                this.current_article.authors.add(p);
                break;
            case "affiliation":
                this.current_author.id = Integer.parseInt(attributes.getValue(0));
                break;
            case "article":
                this.current_article = new Article();
                this.current_article.authors = new ArrayList<Person>();

                if(attributes != null){
                    this.current_article.doi = attributes.getValue(0);
                }

                this.articles.add(current_article);
                break;
            case "author":
                this.current_author = new Author();
                this.current_author.id = Integer.parseInt(attributes.getValue(0));
                this.authors.add(this.current_author);
                break;
            case "title":
                break;
            case "publicationYear":
                break;
            case "journal":
                this.current_journal = new Journal();

                if(attributes != null){
                    this.current_journal.issn = attributes.getValue(0);
                }

                this.current_article.journal = this.current_journal;
                break;
        }

        contents = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName){
            case "author":
                this.current_author.authorName = contents.toString();
                break;
            case "affiliation":
                this.affiliations.put(this.current_author.id, contents.toString());
            case "article":
                break;
            case "title":
                this.current_article.title = contents.toString();
                break;
            case "publicationYear":
                this.current_article.publicationYear = Integer.parseInt(contents.toString());
                break;
            case "journal":
                this.current_article.journal.journalName = contents.toString();
                break;
        }
    }

    @Override
    public void characters(char[] chars, int start, int length) throws SAXException {
        contents.append(new String(chars, start, length));
    }

    @Override
    public void ignorableWhitespace(char[] chars, int i, int i1) throws SAXException {

    }

    @Override
    public void processingInstruction(String s, String s1) throws SAXException {

    }

    @Override
    public void skippedEntity(String s) throws SAXException {

    }

    public ArrayList<Article> searchByAuthorName(String author_name){
        ArrayList<Article> results = new ArrayList<>();
        Person searched_person = null;

        for(Author a: this.authors){
            if(a.authorName.equals(author_name)){
                searched_person = new Person(a.id);
            }
        }

        for(Article a: this.articles){
            if(a.authors.contains(searched_person)){
                results.add(a);
            }
        }

        return results;
    }
}
