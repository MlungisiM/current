package common_actions;

import base.base_class;
import utilities.ObjectMap;
import utilities.UserDefinedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class common_actions extends base_class {

    public static final int AUT_MAX_WAIT = 60;
    //max wait time for object load

    public static WebDriverWait getWait() {
        return new WebDriverWait(driver.get(), Duration.ofSeconds(AUT_MAX_WAIT));
    }


    public static void click(String UIName, String objTechName) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(AUT_MAX_WAIT));
            WebElement elementToClick = driver.get().findElement(ObjectMap.getLocator(objTechName));
            wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
            elementToClick.click();
        } catch (Exception e) {
            throw new UserDefinedException("<<< Unable to Click button " + UIName + " >>> " + e.getMessage());
        }
    }
        public static void clear(String UIName, String objTechName) throws Exception {
            try {
                WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(AUT_MAX_WAIT));
                WebElement elementToClick = driver.get().findElement(ObjectMap.getLocator(objTechName));
                wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
                elementToClick.clear();
            } catch (Exception e) {
                throw new UserDefinedException("<<< Unable to Click button " + UIName + " >>> " + e.getMessage());
            }
        }


}
