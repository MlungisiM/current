package configurations;

import factory.DriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WrapsDriver; // Import WrapsDriver
import java.io.File;
import java.io.IOException;

@Slf4j
public class screenshotUtil {

    public static String takeScreenshot(String screenshotName) {
        // Get the current driver instance from ThreadLocal via DriverFactory
        WebDriver driver = DriverFactory.getDriver();
        TakesScreenshot ts;

        // --- SAFE CASTING LOGIC FOR HEALENIUM ---
        if (driver instanceof TakesScreenshot) {
            // If the driver instance itself supports TakesScreenshot directly, use it.
            ts = (TakesScreenshot) driver;
        } else if (driver instanceof WrapsDriver) {
            // If it's a proxy/wrapper (like SelfHealingDriver), unwrap it to get the original driver.
            WebDriver unwrappedDriver = ((WrapsDriver) driver).getWrappedDriver();
            if (unwrappedDriver instanceof TakesScreenshot) {
                ts = (TakesScreenshot) unwrappedDriver;
            } else {
                log.error("Unwrapped driver does not support taking screenshots.");
                return null; // Cannot take screenshot
            }
        } else {
            log.error("Current driver instance does not support taking screenshots.");
            return null; // Cannot take screenshot
        }
        // --- END SAFE CASTING LOGIC ---


        try {
            File source = ts.getScreenshotAs(OutputType.FILE);

            // Save screenshots in target/reports/screenshots
            String folderPath = System.getProperty("user.dir") + "/target/reports/screenshots/";
            File directory = new File(folderPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fileName = screenshotName + ".png";
            String fullPath = folderPath + fileName;

            File destination = new File(fullPath);
            FileUtils.copyFile(source, destination);

            log.info("Screenshot saved at: " + fullPath);

            // Return relative path for Extent report
            return "screenshots/" + fileName;
        } catch (IOException e) {
            log.error("Screenshot could not be taken: " + e.getMessage(), e);
            return null;
        }
    }
}
