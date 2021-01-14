package CamelComponents;

import org.apache.camel.builder.RouteBuilder;


public class CamelValidatorRoute extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        from("file:camel/input/")
                .to("validator:file:schema.xsd")
                .to("file:camel/output/?fileExist=Append");
    }
}
