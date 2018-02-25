package pip.lab4.ejb;

import org.eclipse.persistence.jpa.config.Array;
import pip.lab4.orm.Point;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@LocalBean
@Stateful
public class PointEJB {
    private List<Point> pointList = new ArrayList<Point>();

    public void addPoint(double x, double y, double radius){
        Point point = new Point(x, y, radius);
        pointList.add(point);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(point);
        transaction.commit();
        entityManager.close();
    }

    public List<Point> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }
}
