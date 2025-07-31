package utilities;

import base.base_class;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends base_class implements ITestListener {

    private static ExtentReports extent;
    private static ExtentSparkReporter reporter;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public void onStart(ITestContext context) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/automation-report.html");

        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Cregalink UI Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", System.getProperty("username"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getDescription();
        if (testName == null || testName.isEmpty()) {
            testName = result.getMethod().getMethodName();
        }
        ExtentTest extentTest = extent.createTest(testName)
                .assignAuthor("Mlungisi_Mbele")
                .assignCategory("Regression");
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getDescription();
        if (testName == null || testName.isEmpty()) {
            testName = result.getMethod().getMethodName();
        }

        // Take screenshot, assuming it saves as ./reports/<testName>.png
        takeScreenshot(testName);

        try {
            test.get().log(Status.PASS, testName + " executed successfully")
                    .info(MediaEntityBuilder.createScreenCaptureFromPath("./reports/" + testName + ".png").build());
        } catch (Exception e) {
            test.get().log(Status.PASS, testName + " executed successfully (screenshot failed to attach)");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getDescription();
        if (testName == null || testName.isEmpty()) {
            testName = result.getMethod().getMethodName();
        }

        takeScreenshot(testName);

        try {
            test.get().log(Status.FAIL, testName + " failed. See stacktrace/logs for details.")
                    .info(MediaEntityBuilder.createScreenCaptureFromPath("./reports/" + testName + ".png").build());
        } catch (Exception e) {
            test.get().log(Status.FAIL, testName + " failed. Screenshot failed to attach.");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getDescription();
        if (testName == null || testName.isEmpty()) {
            testName = result.getMethod().getMethodName();
        }
        test.get().log(Status.SKIP, testName + " was skipped.");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    // Other overrides (onTestFailedButWithinSuccessPercentage) can remain default or empty
}
