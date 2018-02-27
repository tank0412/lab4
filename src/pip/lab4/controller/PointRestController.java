package pip.lab4.controller;

import com.sun.jersey.spi.inject.Inject;
import pip.lab4.ejb.PointEJB;
import pip.lab4.orm.Point;

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

    @Inject
    private PointEJB pointEJB;

    @GET
    @Path("/test")
    public String testPointEJB(){
        pointEJB.addPoint(1, 1,1);
        return "OK";
    }

    @POST
    @Path("/form")
    public String addPointFronForm(@QueryParam("xHidden") String x,
                                   @QueryParam("yHidden") String y,
                                   @QueryParam("radiusHidden") String radius){
        pointEJB.addPoint(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(radius));
        //pointEJB.addPoint(0,0,0);
        //pointEJB.addPoint(x, y, radius);
        return "ok";
    }

    @POST
    @Path("/check")
    @Consumes("application/json")
    public String addPointToDB(PointSimple point,
                      @Context HttpServletRequest httpServletRequest,
                      @Context HttpServletResponse httpServletResponse){
        pointEJB.addPoint(point.getX(), point.getY(), point.getRadius());
//        List<Point> list = (List<Point>)httpServletRequest.getSession().getAttribute("points");
//        list.add(point);
//
//        try {
//            httpServletResponse.sendRedirect("/lab4_war_exploded/index.html");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "ok";
    }

    @GET
    @Path("/point")
    @Produces("application/json")
    public Point testJson(){
        return new Point(1, 1, 1);
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
