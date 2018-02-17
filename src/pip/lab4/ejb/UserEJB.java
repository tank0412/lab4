package pip.lab4.ejb;

import pip.lab4.orm.Group;
import pip.lab4.orm.User;
import pip.lab4.utils.AuthenticationUtils;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class UserEJB {

    public User createUser(String login, String password){
        try {
            password = (AuthenticationUtils.encodeSHA256(password));

        } catch (Exception exception){
            exception.printStackTrace();
        }
        User user = new User(login, password);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(user);
        entityManager.persist(new Group(login, Group.USER_GROUP));
        transaction.commit();
        entityManager.close();
        return user;
    }

    public java.util.List findUserById(String login){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        List resultList = entityManager.createNamedQuery("findUserByLogin")
                .setParameter("login", login)
                .getResultList();

        transaction.commit();
        entityManager.close();
        return resultList;
    }
}
