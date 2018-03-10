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

    @POST
    @Path("/query")
    public void addPointFromQuery(@QueryParam("x") String x,
                                 @QueryParam("y") String y,
                                 @QueryParam("radius") String radius,
                                 @QueryParam("inside") String inside,
                                 @Context HttpServletRequest httpServletRequest,
                                 @Context HttpServletResponse httpServletResponse){
        try {
            Point point = new Point(Double.parseDouble(x),
                    Double.parseDouble(y),
                    Double.parseDouble(radius),
                    inside.equals("true"));
            pointEJB.addPoint(point);
            List<Point> list = (List<Point>)httpServletRequest.getSession().getAttribute("points");
            list.add(point);
            //httpServletResponse.sendRedirect("lab4/frontend/public/index.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("/form")
    public void addPointFromForm(@FormParam("xHidden") String x,
                       @FormParam("yHidden") String y,
                       @FormParam("radiusHidden") String radius,
                       @FormParam("insideHidden") String inside,
                       @Context HttpServletRequest httpServletRequest,
                       @Context HttpServletResponse httpServletResponse) throws IOException {
        try{
            Point point = new Point(Double.parseDouble(x),
                    Double.parseDouble(y),
                    Double.parseDouble(radius),
                    inside.equals("true"));
            String login = (String) httpServletRequest.getSession().getAttribute("login");
            if(login == null)
                throw new Exception();
            pointEJB.addPoint(point);
            List<Point> list = (List<Point>)httpServletRequest.getSession().getAttribute("points");
            list.add(point);
        } catch (Exception e)
        {
            httpServletResponse.sendRedirect("/lab4/signin.html");
        }
    }

    @POST
    @Path("/json")
    @Consumes("application/json")
    public void addPointToDB(Point point,
                             @Context HttpServletRequest httpServletRequest,
                             @Context HttpServletResponse httpServletResponse) throws Exception {
        try {
            String login = (String) httpServletRequest.getSession().getAttribute("login");
            if(login == null)
                throw new Exception();
            pointEJB.addPoint(point);
            List<Point> list = (List<Point>)httpServletRequest.getSession().getAttribute("points");
            list.add(point);

        } catch (Exception e) {
            httpServletResponse.sendRedirect("/lab4/signin.html");
        }
    }

    @GET
    @Path("/list")
    @Produces("application/json")
    public List<Point> getPoints(@Context HttpServletRequest httpServletRequest){
        return (List<Point>) httpServletRequest.getSession().getAttribute("points");
    }
}
