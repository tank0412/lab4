package pip.lab4.controller;

import pip.lab4.ejb.UserEJB;
import pip.lab4.orm.User;

import javax.ws.rs.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/user")
public class UserRestController {
    private static final Pattern pattern = Pattern.compile("(?=.*[a-zA-Z])[a-zA-Z0-9]{3,}");

    @Path("/helloworld")
    @GET
    public String getClichedMessage() {
        return "Hello World";
    }

    @POST
    @Path("/signup")
    @Produces("application/json")
    @Consumes("application/json")
    public User signUp(@FormParam("login") String login,
                       @FormParam("password") String password,
                       @FormParam("repeatPassword") String repeatPassword){
        Matcher matcher = pattern.matcher(login);
        if (!matcher.find()){
            return null;
        }
        if(!password.equals(repeatPassword)){
            return null;
        }
        UserEJB userEJB = new UserEJB();
        List resultList = userEJB.findUserById(login);
        if (resultList.isEmpty()){
            return null;
        }
        return userEJB.createUser(login, password);
    }
}
