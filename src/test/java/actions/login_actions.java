package actions;

import base.base_class;
import factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.login_page;

import java.time.Duration;

public class login_actions extends base_class {

    private login_page _login_page;

    public login_actions() {
        if (getDriver() == null) {
            throw new IllegalStateException("WebDriver is not initialized.");
        }
        _login_page = new login_page(); // instantiate safely only when driver is ready
    }

    public void login() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("frmlogin:username")));
        usernameInput.sendKeys("TE000159");
        _login_page.login_button.click();



        //        String username = DriverFactory.prop.getProperty("username");
        // String password = getProperty("password"); // if needed

//        if (username == null || username.isEmpty()) {
//            log.error("Username is missing in properties file.");
//            throw new IllegalStateException("Missing required property: username");
//        }
//
//        try {
//            _login_page.username_textbox.sendKeys(username);
//            // _login_page.password_textbox.sendKeys(password); // if password is needed
//            _login_page.login_button.click();
//            log.info("User logged in successfully with username: {}", username);
//        } catch (Exception e) {
//            log.error("Login failed: {}", e.getMessage(), e);
//            throw e;
//        }
    }
}
