package base;

import factory.driver_factory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class base_class extends driver_factory {

    public static Logger log = LogManager.getLogger(base_class.class);
    public static Properties prop = null;

    @BeforeMethod
    public static void setUp() throws Exception {
        LaunchBrowser();
    }

    @AfterMethod
    //Tear down method - return driver into its initial state
    public static void tearDown() {
        driver_factory.quitDriver();
    }

    //Load web_config.properties file
    public base_class() {
        prop = new Properties();
        String path = System.getProperty("user.dir") + "/src/main/resources/WebConfig.properties";

        try (FileInputStream propFile = new FileInputStream(path)) {
            prop.load(propFile);
        } catch (IOException e) {
            log.error("Failed to load properties file: " + e.getMessage());
            throw new RuntimeException("Failed to load properties file: " + path, e);
        }
    }

    // Thread-safe screenshot method
    public void takeScreenshot(String screenshotName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);
            String dest = System.getProperty("user.dir") + "/reports/" + screenshotName + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot taken: " + dest);
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
