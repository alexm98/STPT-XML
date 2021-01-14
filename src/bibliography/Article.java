package bibliography;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="article")
@XmlAccessorType(XmlAccessType.FIELD)
public class Article {
    @XmlAttribute
    public String doi;
    @XmlElementWrapper(name="authors")
    @XmlElement(name = "person")
    public ArrayList<Person> authors;
    @XmlElement
    public String title;
    @XmlElement(name = "publicationYear")
    public int publicationYear;
    @XmlElement
    public Journal journal;

    public Article(){
    }

    public Article(String doi, ArrayList authors, String title, int pyear, String jname, String jissn){
        this.doi = doi;
        this.authors = authors;
        this.title = title;
        this.publicationYear = pyear;
        this.journal = new Journal(jname, jissn);
    }

    public String toString(){
        return this.doi + " : " + this.title + " - " + this.authors.toString() + " " + this.journal.toString() + ", " + this.publicationYear;
    }
}
