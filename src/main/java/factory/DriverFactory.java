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

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    public static Actions actions;
    public static Properties prop;

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void removeDriver() {
        driver.remove();
    }

    public static WebDriver initDriver() {
        if (getDriver() == null) {
            launchBrowser(); // launches & sets driver
        }
        return getDriver(); // ✅ always return a usable driver
    }




    public static void launchBrowser() {
        String browserName = prop.getProperty("browser", "chrome").toLowerCase();

        // Configure Jenkins CSP (optional - move if not always needed)
        System.setProperty("hudson.model.DirectoryBrowserSupport.CSP",
                "sandbox allow-scripts; default-src 'self'; script-src * 'unsafe-eval'; img-src *; style-src * 'unsafe-inline'; font-src *");

        try {
            WebDriver webDriver;
            switch (browserName) {
                case "chrome" -> {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                    //chromeOptions.addArguments("--headless=new");
                    webDriver = new ChromeDriver(chromeOptions);
                    log.info("Starting Chrome driver");
                }
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions().addArguments("--headless");
                    webDriver = new FirefoxDriver(firefoxOptions);
                    log.info("Starting Firefox driver");
                }
                case "edge" -> {
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions().addArguments("--headless=new");
                    webDriver = new EdgeDriver(edgeOptions);
                    log.info("Starting Edge driver");
                }
                default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
            }

            setDriver(webDriver);

            long start = System.currentTimeMillis();
            webDriver.get(prop.getProperty("external_dev_environment.url"));
            long duration = (System.currentTimeMillis() - start) / 1000;
            log.info("Page load time: {} seconds", duration);

            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            actions = new Actions(webDriver);

        } catch (Exception e) {
            log.error("Unable to start {} browser: {}", browserName, e.getMessage(), e);
        }
    }


}
