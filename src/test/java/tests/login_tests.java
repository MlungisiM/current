package tests;

import actions.login_actions;
import base.base_class;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.TestListener;

@Listeners(TestListener.class)
public class login_tests extends base_class {

    login_actions login;

    @BeforeMethod
    public void initPages() {
        login = new login_actions(); // Now it's safe, driver is ready
    }

    @Test(testName = "Login Successfully")
    public void testLoginSuccess() {
        login.login();
    }
}