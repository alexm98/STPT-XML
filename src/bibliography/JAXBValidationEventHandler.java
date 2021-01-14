package bibliography;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

public class JAXBValidationEventHandler implements ValidationEventHandler {
    public boolean handleEvent(ValidationEvent event) {
        // handle validation events
        // if you return false the marshal operation is stopped
        return true;
    }
}
