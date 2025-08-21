package tests;

import actions.login_actions;
import base.base_class;
import org.apache.hc.core5.reactor.Command;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.TestListener;

@Listeners(TestListener.class)
public class login_tests extends base_class {

    login_actions login;

    @BeforeMethod()
    public void initPages() throws Exception {
        super.setUp();          // ✅ This initializes WebDriver properly
        login = new login_actions();
        login.init();           // ✅ Now getDriver() is not null
    }

    @Test(testName = "Login Successfully")
    public void Login_Successfully() throws Exception {login.loginValidUsername();}

//    @Test(testName = "Login with incorrect username")
//    public void LoginIncorrectUsername() {
//        login.loginIncorrectUsername();
//    }

//    @Test(testName = "Login with empty username")
//    public void LoginEmptyUsername() {
//        login.loginEmptyUsername();
//    }


}