package actions;

import base.base_class;
import factory.DriverFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pages.login_page;

import java.time.Duration;


public class login_actions extends base_class {

    private login_page _login_page;

    public void init() {
        if (getDriver() == null) {
            throw new IllegalStateException("WebDriver is not initialized.");
        }
        _login_page = new login_page();
    }


    public void loginValidUsername() throws Exception {

        String sso_username = DriverFactory.prop.getProperty("sso_username");
        String sso_password = DriverFactory.prop.getProperty("sso_password");

        String cregalink_username = DriverFactory.prop.getProperty("sso_username");
        String cregalink_password = getProperty("sso_password");

        if (sso_username == null || sso_username.isEmpty() || cregalink_username == null || cregalink_username.isEmpty()) {
            log.error("Username is missing in properties file.");
            throw new IllegalStateException("Missing required property: username");
        } else if (sso_password == null || sso_password.isEmpty()) {
            log.error("Password is missing in properties file.");
            throw new IllegalStateException("Missing required property: username");
        }

        try {
            getWait().until(ExpectedConditions.elementToBeClickable(_login_page.sso_username_textbox));
            _login_page.sso_username_textbox.click();
            _login_page.sso_username_textbox.sendKeys(sso_username);
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            _login_page.sso_next_button.click();
            getWait().until(ExpectedConditions.elementToBeClickable(_login_page.sso_password_textbox));
            _login_page.sso_password_textbox.click();
            _login_page.sso_password_textbox.sendKeys(sso_password);
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            _login_page.sso_signIn_button.click();
            getWait().until(ExpectedConditions.elementToBeClickable(_login_page.mfa_username_textbox));
            _login_page.mfa_username_textbox.click();
            getWait().until(ExpectedConditions.elementToBeClickable(_login_page.mfa_verify_button));
            Assert.assertTrue(_login_page.mfa_verify_button.isDisplayed());
            log.info("User logged in successfully with username: {}", sso_username);
        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage(), e);
            throw e;
        }
    }

//    public void loginIncorrectUsername() {
//
//        String username = DriverFactory.prop.getProperty("password");
//        String password = getProperty("password");
//
//        if (username == null || username.isEmpty()) {
//            log.error("Username is missing in properties file.");
//            throw new IllegalStateException("Missing required property: username");
//        }
//
//        try {
//            _login_page.sso_username_textbox.sendKeys(username);
//            _login_page.sso_password_textbox.sendKeys(password); // if password is needed
//            _login_page.sso_signIn_button.click();
//            log.info("User logged in successfully with username: {}", username);
//        } catch (Exception e) {
//            log.error("Login failed: {}", e.getMessage(), e);
//            throw e;
//        }
//    }

//    public void loginEmptyUsername() {
//
//        String username = DriverFactory.prop.getProperty("");
//        String password = getProperty("sso_password");
//
//           // if (username == null || username.isEmpty()) {
//        try{
//                _login_page.sso_next_button.click();
//                log.info("Username is missing in properties file.");
//                Assert.assertTrue(_login_page.missing_username_message.isDisplayed());
//                throw new IllegalStateException("Missing required property: username");
//            }
//            else {
//                log.error("User was able to login with empty username");
//            }
//
//        }
    }