package pip.lab4.ejb;

import pip.lab4.orm.User;
import pip.lab4.utils.AuthenticationUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UserEJB {
    private EntityManager entityManager;

    public User createUser(User user){
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(User.getPassword()));

        } catch (Exception exception){
            exception.printStackTrace();
        }
        entityManager.persist(user);
        return user;
    }

    public User findUserById(String login){
        TypedQuery<User> query = entityManager.createNamedQuery("findUserByLogin", User.class);
        query.setParameter("login", login);
        User user = query.getSingleResult();
        return user;
    }
}
