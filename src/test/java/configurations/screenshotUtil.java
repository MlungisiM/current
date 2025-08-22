package configurations;

import factory.DriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

@Slf4j
public class screenshotUtil {

    public static String takeScreenshot(String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Relative path from HTML report to screenshot
            String relativePath = "screenshots/" + screenshotName + ".png";
            String fullPath = System.getProperty("user.dir") + "/target/" + relativePath;

            File destination = new File(fullPath);
            FileUtils.copyFile(source, destination);
            log.info("Screenshot taken successfully and saved at: " + fullPath);
            System.out.println("Screenshot saved at: " + fullPath);
            return relativePath; // 🔁 Return relative path, not full
        } catch (IOException e) {
            log.error("screenshot could not be taken: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
