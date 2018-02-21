package pip.lab4.controller;

import pip.lab4.ejb.UserEJB;
import pip.lab4.orm.User;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@LocalBean
@Stateless
@Path("/user")
public class UserRestController {
    private static final Pattern pattern = Pattern.compile("(?=.*[a-zA-Z])[a-zA-Z0-9]{3,}");

    @Path("/helloworld")
    @GET
    @Produces("application/json")
    public User getClichedMessage() {
        return userEJB.createUser("login", "password");
    }

    @POST
    @Path("/signin")
    @Produces("application/json")
    public User signIn(@FormParam("login") String login,
                       @FormParam("password") String password){
        return null;
    }

    @POST
    @Path("/signup")
    @Produces("application/json")
    //@Consumes("application/json")
    public User signUp(@FormParam("login") String login,
                       @FormParam("password") String password,
                       @FormParam("password") String repeatPassword) {
        Matcher matcher = pattern.matcher(login);
        if (!matcher.find()){
            return null;
        }
        if(!password.equals(repeatPassword)){
            return null;
        }
        List resultList = userEJB.findUserById(login);
        if (resultList.isEmpty()){
            return null;
        }
        return userEJB.createUser(login, password);
    }

    @EJB
    private UserEJB userEJB;
}
