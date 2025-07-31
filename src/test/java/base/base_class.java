package base;

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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class base_class extends DriverFactory {

    public static Logger log = LogManager.getLogger(base_class.class);
    private static final Properties prop = new Properties();

    @BeforeMethod
    public void setUp() throws Exception {
        if (DriverFactory.prop == null || DriverFactory.prop.isEmpty()) {
            try (InputStream input = base_class.class
                    .getClassLoader()
                    .getResourceAsStream("web_config.properties")) {

                if (input == null) {
                    throw new RuntimeException("web_config.properties not found in resources.");
                }
                prop.load(input);
                DriverFactory.prop = prop;
            } catch (IOException e) {
                throw new RuntimeException("Failed to load web_config.properties", e);
            }
        }

        WebDriver driver = DriverFactory.initDriver();
        setDriver(driver); // ✅ Make sure setDriver is called
    }

    @AfterMethod
    public void tearDown() {
        if (DriverFactory.getDriver() != null) {
            DriverFactory.getDriver().quit();
            DriverFactory.removeDriver();
        }
    }

    public static Properties getProperties() {
        return prop;
    }

    public static String getProperty(String key) {
        return prop.getProperty(key);
    }

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
