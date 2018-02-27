package pip.lab4.controller;

import com.sun.jersey.spi.inject.Inject;
import com.sun.jersey.spi.resource.Singleton;
import pip.lab4.ejb.UserEJB;
import pip.lab4.orm.Point;
import pip.lab4.orm.User;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.core.Context;
import java.util.ArrayList;

@LocalBean
@Stateful
@Path("/user")
public class UserRestController {
    private static final Pattern pattern = Pattern.compile("(?=.*[a-zA-Z])[a-zA-Z0-9]{3,}");

    @Inject
    private UserEJB userEJB;

    @GET
    @Path("/hello")
    public String helloWorld(){
        return "Hello World";
    }

    @POST
    @Path("/signin")
    public void signIn(@FormParam("login") String login,
                       @FormParam("password") String password,
                       @Context HttpServletRequest httpServletRequest,
                       @Context HttpServletResponse httpServletResponse) throws IOException {
        try {
            boolean isTrueData = userEJB.signIn(login, password);
            if(isTrueData){
                httpServletRequest.getSession().setAttribute("login", login);
                httpServletRequest.getSession().setAttribute("points", new ArrayList<Point>());
                httpServletResponse.sendRedirect("/lab4/index.html");
            }
            else {
                httpServletResponse.sendRedirect("/lab4/signin.html");
                System.err.println("login exists or data is incorrect");
            }
        }
        catch (Exception e) {
            System.err.println("Signin error!");
            e.printStackTrace();
            httpServletResponse.sendRedirect("/lab4/signin.html");
        }
    }

    @POST
    @Path("/signup")
    public void signUp(@FormParam("login") String login,
                       @FormParam("password") String password,
                       @FormParam("repeatPassword") String repeatPassword,
                       @Context HttpServletRequest httpServletRequest,
                       @Context HttpServletResponse httpServletResponse){
        try{
            Matcher matcher = pattern.matcher(login);
            if (!matcher.find()
                    || !password.equals(repeatPassword)
                    || !userEJB.findUserById(login).isEmpty()){
                httpServletResponse.sendRedirect("/lab4/signup.html");
                System.err.println("login exists or data is incorrect");
            }
            userEJB.createUser(login, password);
            httpServletResponse.sendRedirect("/lab4/regdone.html");
        }
        catch (Exception e)
        {
            System.err.println("Signup error!");
            e.printStackTrace();
        }
    }

    @GET
    @Path("/logout")
    public String logOut(@Context HttpServletRequest httpServletRequest,
                       @Context HttpServletResponse httpServletResponse) {
        try {
            httpServletRequest.getSession().invalidate();
            httpServletResponse.sendRedirect("/lab4/signin.html");
        }
        catch (Exception e) {
            System.err.println("Logout error!");
            e.printStackTrace();
        }
        finally {
            return "ok";
        }
    }
}
