package tests;

import actions.daa_submissions_actions;
import actions.login_actions;
import base.base_class;
import configurations.ExtentTestListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ExtentTestListener.class)
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

    @Test(testName = "Login with incorrect username")
    public void Login_using_Incorrect_Username() throws Exception {login.IncorrectUsername();}
    
    @Test(testName = "Login with empty username")
    public void Login_using_Empty_Username() throws Exception {login.loginEmptyUsername();}

    @Test(testName = "Login with an incorrect username")
    public void Login_using_Incorrect_Password() throws Exception {login.IncorrectPassword();}

    @Test(testName = "Login with an empty password")
    public void Login_using_Empty_Password() throws Exception {login.loginEmptyPassword();}
}