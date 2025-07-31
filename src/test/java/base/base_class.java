package base;

import configurations.ConfigReader;
import factory.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class base_class extends DriverFactory {

    public static Logger log = LogManager.getLogger(base_class.class);
    private static final Properties prop = new Properties();

    @BeforeMethod
    public void setUp() throws Exception {
        DriverFactory.prop = ConfigReader.loadProperties("web_config.properties");
        WebDriver driver = DriverFactory.initDriver(); // FIXED: this now returns driver
    }


    @AfterMethod
    public void tearDown() {
        if (DriverFactory.getDriver() != null) {
            DriverFactory.getDriver().quit();
            DriverFactory.removeDriver();
        }
    }

    public base_class()
    {
        try (InputStream input = base_class.class
                .getClassLoader()
                .getResourceAsStream("web_config.properties")) {

            if (input == null) {
                throw new RuntimeException("web_config.properties not found in resources.");
            }
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load web_config.properties", e);
        }
    }
    public static Properties getProperties() {
        return prop;
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
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
