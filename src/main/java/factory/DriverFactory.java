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
import java.util.UUID;

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

    public static WebDriver initDriver() throws Exception {
        if (getDriver() == null) {
            launchBrowser(); // launches & sets driver
        }
        return getDriver(); // ✅ always return a usable driver
    }

    public static void launchBrowser() throws Exception {
        String browserName = prop.getProperty("browser").toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(prop.getProperty("headless", "true"));

//        // Set Hudson CSP properties
//        System.clearProperty("hudson.model.DirectoryBrowserSupport.CSP");
//        System.setProperty("hudson.model.DirectoryBrowserSupport.CSP",
//                "sandbox allow-scripts; default-src 'self'; script-src * 'unsafe-eval'; img-src *; style-src * 'unsafe-inline'; font-src *");

        try {
            switch (browserName) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    // Create unique user data directory
                    String userDataDir = "/tmp/chrome_profile_" + UUID.randomUUID().toString();
                    chromeOptions.addArguments("--user-data-dir=" + userDataDir);
                    // Other common options
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--no-sandbox");
                    if (isHeadless) chromeOptions.addArguments("--headless=new");
                    driver.set(new ChromeDriver(chromeOptions));
                    log.info("Chrome driver started");
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) firefoxOptions.addArguments("--headless");
                    driver.set(new FirefoxDriver(firefoxOptions));
                    log.info("Firefox driver started");
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (isHeadless) edgeOptions.addArguments("--headless=new");
                    driver.set(new EdgeDriver(edgeOptions));
                    log.info("Edge driver started");
                    break;

                default:
                    throw new RuntimeException("Unsupported browser: " + browserName);
            }

            long startTime = System.currentTimeMillis();
            getDriver().get(prop.getProperty("external_dev_environment.url"));
            getDriver().manage().window().maximize();
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            actions = new Actions(driver.get());
            long totalTime = (System.currentTimeMillis() - startTime) / 1000;
            log.info("Total time to load URL: {} seconds", totalTime);

        } catch (Exception e) {
            log.error("Unable to start {} browser", browserName, e);
            throw e;
        }
    }
        private static boolean isCIEnvironment() {
            return System.getenv("TF_BUILD") != null ||
                    System.getenv("CI") != null ||
                    System.getenv("JENKINS_HOME") != null;
        }
    }