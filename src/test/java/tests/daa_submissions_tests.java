package tests;

import actions.daa_submissions_actions;
import actions.login_actions;
import base.base_class;
import configurations.ExtentTestListener;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(ExtentTestListener.class)
public class daa_submissions_tests extends base_class {

    daa_submissions_actions submissions;
    login_actions login;


    @BeforeMethod()
    public void initPages() throws Exception {
        super.setUp(); // initializes WebDriver

        // Initialize login actions
        login = new login_actions();
        login.init();

        // Initialize submissions actions
        submissions = new daa_submissions_actions();
        submissions.init();
    }


    @Test(testName = "Submit a valid DAA file")
        public void Valid_Excel_File_Submission() throws Exception {
        login.loginValidUsername();
        submissions.Submit_Valid_Excel_DAA();
    }


    @Test(testName = "Submit a valid CSV DAA file")
        public void Valid_CSV_File_Submission() throws Exception {
        login.loginValidUsername();
        submissions.Submit_Valid_CSV_DAA();}
    }
