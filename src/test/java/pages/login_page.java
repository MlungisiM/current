package pages;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class login_page {

    private WebDriver driver;

    @FindBy(id = "i0116")
    public WebElement sso_username_textbox;

    @FindBy(id = "idSIButton9")
    public WebElement sso_next_button;

    @FindBy(id = "i0118")
    public WebElement sso_password_textbox;

    @FindBy(id = "frmlogin:password")
    public WebElement cregalink_password_textbox;

    @FindBy(id = "idSIButton9")
    public WebElement sso_signIn_button;

    @FindBy(id = "frmlogin:username")
    public WebElement cregalink_username_button;

    @FindBy(id = "idA_PWD_ForgotPassword")
    public WebElement forgot_password_link;

    @FindBy(id = "frmlogin")
    public WebElement missing_username_message;

    @FindBy(id = "tileList")
    public WebElement mfa_username_textbox;

    @FindBy(id = "oneTimeCodePrimaryButton")
    public WebElement mfa_verify_button;

    @FindBy(id = "frmlogin:btnlogin")
    public WebElement cregalink_login_button;

    public login_page() {
        this.driver = DriverFactory.getDriver();
        if (this.driver == null) {
            throw new IllegalStateException("Driver is null in LoginPage constructor.");
        }
        PageFactory.initElements(driver, this);
    }
}