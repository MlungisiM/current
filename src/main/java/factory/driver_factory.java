package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;
import java.util.Properties;

public class driver_factory {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static Actions actions;
    public static Logger log = LogManager.getLogger(driver_factory.class);
    public static Properties prop = null;

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void LaunchBrowser() throws Exception {

        String browserName = prop.getProperty("browser");
        System.clearProperty("hudson.model.DirectoryBrowserSupport.CSP");
        System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "sandbox allow-scripts; default-src 'self'; script-src * 'unsafe-eval'; img-src *; style-src * 'unsafe-inline'; font-src *");
        try {

            if ("chrome".equalsIgnoreCase(browserName)) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();// Create options for Firefox
                options.addArguments("--headless=new");// Run in headless mode
                driver.set(new ChromeDriver(options));
                log.info("Starting Chrome driver");
            } else if ("firefox".equalsIgnoreCase(browserName)) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions(); // Create options for Firefox
                options.addArguments("--headless");// Run in headless mode
                driver.set(new FirefoxDriver(options));
                log.info("Starting firefox driver");
            } else if ("edge".equalsIgnoreCase(browserName)) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--headless=new");// Run in headless mode
                driver.set(new EdgeDriver(options));
                log.info("Starting Edge driver");
            }
            long start = System.currentTimeMillis();
            driver.get().get(prop.getProperty("acc59.url"));
            long finish = System.currentTimeMillis();
            long Total = (finish - start) / 1000;
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            System.out.println("Total time it took the system to respond is: " + Total + " seconds");
            log.info("Total time it took the system to respond is: " + Total + " seconds");
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            actions = new Actions(driver.get());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("unable to start " + browserName + " browser");
            log.info("unable to start " + browserName + " browser");
        }
        driver_factory.setDriver(driver.get());
        driver_factory.getDriver().manage().window().maximize();
    }

    public static void quitDriver() {
        try {
            driver.get().quit();
            driver.remove(); // Prevent memory leaks
        }
        catch (Exception e) {
            log.error("could not quit driver: "+e.getMessage());
        }
    }
}
