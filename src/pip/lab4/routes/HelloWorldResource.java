package pip.lab4.routes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/helloworld")
public class HelloWorldResource{

    @GET
    public String getClichedMessage() {
        return "Hello World";
    }
}
