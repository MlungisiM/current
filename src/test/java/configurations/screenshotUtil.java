package configurations;

import factory.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class screenshotUtil {

    public static String takeScreenshot(String testName) {
        try {
            WebDriver driver = DriverFactory.getDriver(); // thread-safe access
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String path = "reports/" + testName + ".png";
            FileUtils.copyFile(src, new File(path));
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
