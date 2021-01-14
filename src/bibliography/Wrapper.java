package bibliography;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Wrapper {
    @XmlElementWrapper(name="articles")
    @XmlElement(name="article")
    private List<Article> articles;
    @XmlElementWrapper(name="authors")
    @XmlElement(name="author")
    private List<Author> authors;
    @XmlElementWrapper(name="affiliations")
    @XmlElement(name="affiliation")
    private List<Affiliation> affiliations;

    public Wrapper() {
        articles = new ArrayList<Article>();
    }

    public List<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Author> getAuthors() {
        return this.authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Affiliation> getAffiliations() {
        return this.affiliations;
    }

    public void setAffiliations(List<Affiliation> affiliations) {
        this.affiliations = affiliations;
    }
}

