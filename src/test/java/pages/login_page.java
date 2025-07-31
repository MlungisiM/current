package pages;

import factory.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class login_page {

    private WebDriver driver;

    @FindBy(id = "frmlogin:username")
    public WebElement username_textbox;

    @FindBy(id = "frmlogin:password")
    public WebElement password_textbox;

    @FindBy(id = "frmlogin:btnlogin")
    public WebElement login_button;

    @FindBy(id = "frmlogin:lostpwd")
    public WebElement forgot_password_link;

    public login_page() {
        this.driver = DriverFactory.getDriver();
        if (this.driver == null) {
            throw new IllegalStateException("Driver is null in LoginPage constructor.");
        }
        PageFactory.initElements(driver, this);
    }
}