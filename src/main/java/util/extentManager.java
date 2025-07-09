package util;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

/**
 * Wrapper for ExtentReports management
 * Delegates to CustomExtentReportManager for actual implementation
 */
public class extentManager {
    /**
     * Get the ExtentReports instance
     */
    public static ExtentReports getExtentReports() {
        return CustomExtentReportManager.getExtentReports();
    }

    /**
     * Create a new test with the given name
     */
    public static ExtentTest createTest(String testName) {
        return CustomExtentReportManager.createTest(testName);
    }

    /**
     * Create a new test with name and description
     */
    public static ExtentTest createTest(String testName, String description) {
        return CustomExtentReportManager.createTest(testName, description);
    }

    /**
     * Flush all reports to disk
     */
    public static void flushReports() {
        CustomExtentReportManager.flushReports();
    }

    /**
     * Get the current test instance
     */
    public static ExtentTest getTest() {
        return CustomExtentReportManager.getTest();
    }
    
    /**
     * Set the current test instance
     */
    public static void setTest(ExtentTest test) {
        CustomExtentReportManager.setTest(test);
    }
}
