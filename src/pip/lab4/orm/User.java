package pip.lab4.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = "findUserByLogin", query =
                "SELECT user FROM User user WHERE user.login = :login"),
        @NamedQuery(name = "login", query =
                "SELECT user FROM User user WHERE user.login = :login AND user.password = :password")
})
@Table(name = "Users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    public User(){}

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
