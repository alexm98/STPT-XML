package bibliography;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="affiliation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Affiliation {
    @XmlValue
    public String institution;
    @XmlAttribute(name="rid")
    public Integer researcher_id;

    public Affiliation(){

    }

    public Affiliation(String institution, Integer researcher_id){
        this.institution = institution;
        this.researcher_id = researcher_id;
    }

    public String toString(){
        return this.researcher_id + " from " + this.institution;
    }
}
