package pip.lab4.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = "findUserById", query = "SELECT u FROM UserPersistence u WHERE u.login = :login")
})
@Table(name = "Users")
public class UserPersistence {
    private static final long serialVersionUID = 1L;
    private String login;
    private String password;

    public UserPersistence(){}
    public UserPersistence(String login, String password){
        this.login = login;
        this.password = password;
    }

    @Id @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
