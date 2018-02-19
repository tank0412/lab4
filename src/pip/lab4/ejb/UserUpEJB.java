package pip.lab4.ejb;

import pip.lab4.orm.Group;
import pip.lab4.orm.User;
import pip.lab4.utils.AuthenticationUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@LocalBean
@Stateless
public class UserUpEJB {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
    private EntityManager entityManager;

    public User createUser(String login, String password){
        try {
            password = (AuthenticationUtils.encodeSHA256(password));

        } catch (Exception exception){
            exception.printStackTrace();
        }
        User user = new User(login, password);

        EntityTransaction transaction = SetEntityTransaction();
        transaction.begin();

        entityManager.persist(user);
        entityManager.persist(new Group(login, Group.USER_GROUP));
        transaction.commit();
        entityManager.close();
        return user;
    }

    public java.util.List findUserById(String login){ ;
        EntityTransaction transaction = SetEntityTransaction();
        transaction.begin();

        List resultList = entityManager.createNamedQuery("findUserByLogin")
                .setParameter("login", login)
                .getResultList();

        transaction.commit();
        entityManager.close();
        return resultList;
    }

    private EntityTransaction SetEntityTransaction(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.getTransaction();
    }
}
