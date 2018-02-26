package pip.lab4.controller;

import pip.lab4.ejb.PointEJB;
import pip.lab4.orm.Point;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.List;

@LocalBean
@Stateful
@Path("/point")
public class PointRestController {

    @EJB
    private PointEJB pointEJB;

    @POST
    @Path("/check")
    @Consumes("application/json")
    public void addPointToDB(Point point,
                      @Context HttpServletRequest httpServletRequest,
                      @Context HttpServletResponse httpServletResponse){
        pointEJB.addPoint(point.getX(), point.getY(), point.getRadius());
        List<Point> list = (List<Point>)httpServletRequest.getSession().getAttribute("points");
        list.add(point);

        try {
            httpServletResponse.sendRedirect("/lab4_war_exploded/index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/list")
    @Produces("application/json")
    public Point getPoints(@Context HttpServletRequest httpServletRequest){
        return new Point(1, 2, 3);
        //return (List<Point>) httpServletRequest.getSession().getAttribute("points");
    }

    @GET
    @Path("/hello")
    public String getHello(){
        return "hello";
    }
}
