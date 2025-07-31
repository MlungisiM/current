package actions;

import base.base_class;
import factory.DriverFactory;
import org.testng.annotations.Listeners;
import pages.login_page;
import utilities.TestListener;

@Listeners(TestListener.class)
public class login_actions extends base_class {

    login_page _login_page =  new login_page();


    public void login() {
        String username = DriverFactory.prop.getProperty("username");
        // String password = getProperty("password"); // Uncomment if needed

        if (username == null || username.isEmpty()) {
            log.error("Username is missing in properties file.");
            throw new IllegalStateException("Missing required property: username");
        }

        try {
            _login_page.username_textbox.sendKeys(username);
            // _login_page.password_textbox.sendKeys(password); // if password is required
            _login_page.login_button.click();
            log.info("User logged in successfully with username: {}", username);
        } catch (Exception e) {
            log.error("User login failed: {}", e.getMessage(), e);
            throw e; // rethrowing helps fail the test explicitly if required
        }
    }

}
