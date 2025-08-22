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

            // ✅ Folder under target/reports/screenshots
            String folderPath = System.getProperty("user.dir") + "/target/reports/screenshots/";
            new File(folderPath).mkdirs(); // ensure folder exists

            String fileName = screenshotName + ".png";
            String fullPath = folderPath + fileName;

            File destination = new File(fullPath);
            FileUtils.copyFile(source, destination);

            log.info("Screenshot saved at: " + fullPath);

            // ✅ return relative path from report to screenshot
            return "screenshots/" + fileName;
        } catch (IOException e) {
            log.error("Screenshot could not be taken: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}