package configurations;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {

    private static final Map<Long, ExtentTest> extentTestMap = new HashMap<>();
    private static final ExtentReports extent = ExtentManager.getExtentReports();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get(Thread.currentThread().getId());
    }

    public static synchronized void startTest(String testName, String description) {
        ExtentTest test = extent.createTest(testName, description);
        extentTestMap.put(Thread.currentThread().getId(), test);
    }

    public static synchronized void endTest() {
        extent.flush();
    }

    // Attach screenshot to current test
    public static synchronized void logScreenshot(String screenshotName) {
        String path = screenshotUtil.takeScreenshot(screenshotName);
        if (path != null) {
            getTest().addScreenCaptureFromPath(path);
        }
    }
}
