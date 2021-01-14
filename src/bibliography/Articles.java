package bibliography;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="articles")
@XmlAccessorType(XmlAccessType.FIELD)
public class Articles {
    @XmlElement(name="article")
    private List<Article> articles;

    public Articles() {
        articles = new ArrayList<Article>();
    }

    public List<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}

