package bibliography;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "journal")
public class Journal {
    @XmlValue
    public String journalName;
    @XmlAttribute
    public String issn;

    public Journal(){
    }

    public Journal(String name, String issn){
        this.journalName = name;
        this.issn = issn;
    }

    public String toString(){
        return this.journalName + " - " + this.issn;
    }
}
