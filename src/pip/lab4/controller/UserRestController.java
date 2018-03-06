package pip.lab4.controller;

import com.sun.jersey.spi.inject.Inject;
import pip.lab4.ejb.UserEJB;
import pip.lab4.orm.Point;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.lang.String;

@LocalBean
@Stateful
@Path("/user")
public class UserRestController {
    private static final Pattern pattern = Pattern.compile("(?=.*[a-zA-Z])[a-zA-Z0-9]{3,}");

    @Inject
    private UserEJB userEJB;

//    @Path("/lol")
//    @GET
//    @Produces({MediaType.TEXT_HTML})
//    public InputStream viewMain(){
//        File file;
//        return
//    }

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
                httpServletResponse.sendRedirect("/lab4/frontend/public/index.html");
            }
            else {
                httpServletResponse.sendRedirect("/lab4/signin.html?error=true");
                System.err.println("login exists or data is incorrect");
            }
        }
        catch (Exception e) {
            System.err.println("Signin error!");
            e.printStackTrace();
            httpServletResponse.sendRedirect("/lab4/signin.html?error=true");
        }
    }

    @POST
    @Path("/signup")
    public void signUp(@FormParam("login") String login,
                       @FormParam("password") String password,
                       @FormParam("repeatPassword") String repeatPassword,
                       @Context HttpServletRequest httpServletRequest,
                       @Context HttpServletResponse httpServletResponse) throws IOException {
        try{
            Matcher matcher = pattern.matcher(login);
            if (!matcher.find()|| login.length()<3){
                httpServletResponse.sendRedirect("/lab4/signup.html?login=incorrect");
                return;
            }
            if(!userEJB.findUserById(login).isEmpty()){
                httpServletResponse.sendRedirect("/lab4/signup.html?login=exist");
                return;
            }
            if(!password.equals(repeatPassword)){
                httpServletResponse.sendRedirect("/lab4/signup.html?password=incorrect");
                return;
            }
            if(password.length() < 5){
                httpServletResponse.sendRedirect("/lab4/signup.html?password=simple");
                return;
            }
            userEJB.createUser(login, password);
            httpServletResponse.sendRedirect("/lab4/regdone.html");
        }
        catch (Exception e)
        {
            System.err.println("Signup error!");
            e.printStackTrace();
            httpServletResponse.sendRedirect("/lab4/signup.html");
        }
    }

    @POST
    @Path("/logout")
    public void logOut(@Context HttpServletRequest httpServletRequest,
                       @Context HttpServletResponse httpServletResponse) {
        try {
            httpServletRequest.getSession().invalidate();
            httpServletResponse.sendRedirect("/lab4/signin.html");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
