package util;

import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    /**
     * Get or initialize the ExtentReports instance with proper configuration
     */
    public static ExtentReports getExtentReports() {
        if (extent == null) {
            extent = new ExtentReports();

            // Setup HTML report
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/cucumber-reports/Spark.html");
            configureSparkReporter(sparkReporter);
            extent.attachReporter(sparkReporter);

            // Setup PDF report properties (will be handled by adapter plugin)
            try {
                // Set system properties for PDF reporter via adapter
                System.setProperty("extent.reporter.pdf.start", "true");
                System.setProperty("extent.reporter.pdf.out", "target/cucumber-reports/ExtentPdf.pdf");
                System.setProperty("extent.reporter.pdf.config", "src/test/resources/pdf-config.properties");
                
                // Log PDF configuration
                System.out.println("PDF reporting configured through system properties");
            } catch (Exception e) {
                System.out.println("PDF Reporter initialization failed, continuing with HTML reports only: " + e.getMessage());
                e.printStackTrace();
            }

            // Set global info
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
        return extent;
    }

    /**
     * Configure the Spark HTML Reporter
     */
    private static void configureSparkReporter(ExtentSparkReporter sparkReporter) {
        try {
            // Check if custom config XML exists
            Path configPath = Paths.get("src/test/resources/extent-config.xml");
            if (Files.exists(configPath)) {
                sparkReporter.loadXMLConfig(configPath.toString());
            } else {
                // Apply default configuration
                sparkReporter.config().setTheme(Theme.STANDARD);
                sparkReporter.config().setDocumentTitle("BDD Maersk App Test Report");
                sparkReporter.config().setReportName("API Test Execution Report");
                sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
            }
        } catch (Exception e) {
            System.out.println("Failed to configure Spark reporter, using defaults: " + e.getMessage());
        }
    }

    public static ExtentTest createTest(String testName) {
        ExtentTest test = getExtentReports().createTest(testName);
        testThreadLocal.set(test);
        return test;
    }

    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = getExtentReports().createTest(testName, description);
        testThreadLocal.set(test);
        return test;
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }

    /**
     * Set the current test instance
     */
    public static void setTest(ExtentTest newTest) {
        testThreadLocal.set(newTest);
    }
}
