package CamelComponents;

import bibliography.Article;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import static org.apache.camel.component.stax.StAXBuilder.stax;

public class StAXCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:camel/input/")
                .split(stax(Article.class)).streaming()
                .choice()
                .when().xpath("//person[@id=\"3\"]").process(
                new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println(exchange.getProperties());
                        System.out.println("Found an article of the author you are searching");
                    }
                }).to("file:camel/output/?fileExist=Append")
                .end().end();
    }
}
