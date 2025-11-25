package configurations;

import actions.login_actions;
import actions.submissions_actions;
import base.base_class;
import org.testng.annotations.BeforeMethod;

public class Hooks extends base_class {

    login_actions login;
    submissions_actions submissions;

    //@BeforeMethod()
    public void initPages() throws Exception {
        super.setUp();          // ✅ This initializes WebDriver properly
        login = new login_actions();
        login.init();
        submissions = new submissions_actions();
        submissions.init();           // ✅ Now getDriver() is not null// ✅ Now getDriver() is not null
    }
}
