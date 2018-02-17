package pip.lab4.routes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/signup")
public class Authentication {

    @POST
    public String signUp(){
        return "LOL";
    }
}
