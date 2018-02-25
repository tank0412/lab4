package pip.lab4.orm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group {
    public static final String USER_GROUP = "users";

    @Id
    @Column(name = "login", length = 255)
    private String login;

    @Column(name = "groupname", length = 31)
    private String groupName;

    public Group(){}

    public Group(String login, String groupName){
        this.login = login;
        this.groupName = groupName;
    }

    public String getLogin() {
        return login;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
