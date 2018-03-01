package pip.lab4.ejb;

import org.eclipse.persistence.jpa.config.Array;
import pip.lab4.orm.Point;

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

    public PointEJB(){}

    public void addPoint(Point point){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(point);
        transaction.commit();
        entityManager.close();
    }
}
