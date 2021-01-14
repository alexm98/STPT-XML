package bibliography;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    @XmlAttribute
    public Integer id;

    public Person(){
    }

    public Person(Integer id){
        this.id = id;
    }

    public String toString(){
        return this.id.toString();
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Integer){
            return this.id == other;
        }
        else if(other instanceof Person){
            return this.id == ((Person) other).id;
        }

        return false;
    }
}
