package pip.lab4.ejb;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateful;
import java.util.logging.Logger;

@Stateful
@PermitAll
public class RegisterEJB {
    private static Logger logger = Logger.getLogger(RegisterEJB.class.getName());

    private UserEJB userEJB;

}
