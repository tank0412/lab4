package pip.lab4.ejb;

import pip.lab4.orm.Group;
import pip.lab4.orm.User;
import pip.lab4.utils.AuthenticationUtils;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@LocalBean
@Stateful
public class UserEJB {

    public UserEJB(){}

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

    public java.util.List findUserById(String login){
        EntityTransaction transaction = SetEntityTransaction();
        transaction.begin();

        List resultList = entityManager.createNamedQuery("findUserByLogin")
                .setParameter("login", login)
                .getResultList();

        transaction.commit();
        entityManager.close();
        return resultList;
    }

    public boolean signIn(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        try {
            EntityTransaction transaction = SetEntityTransaction();
            transaction.begin();
            password = (AuthenticationUtils.encodeSHA256(password));

            User userInDB = (User)entityManager.createNamedQuery("signin")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
            if (userInDB != null)
                return true;
            else
                return false;
        } catch (Exception exception){
            throw exception;
        }
    }

    private EntityTransaction SetEntityTransaction(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("lab4");
        entityManager = entityManagerFactory.createEntityManager();
        return entityManager.getTransaction();
    }
}