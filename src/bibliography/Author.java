 package bibliography;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.FIELD)
public class Author {
    @XmlAttribute
    public Integer id;
    @XmlValue
    public String authorName;

    public Author(){
    }

    public Author(String name, Integer id){
        this.id = id;
        this.authorName = name;
    }

    public String toString(){
        return this.authorName + "(" + this.id.toString() + ")";
    }
}
