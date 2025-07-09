package scriptRunner;

import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@Test 
@CucumberOptions(
        features = "src/test/resources/features/notifications.feature", // Path to your feature files
        glue = {"com.maersk.definations"}, // Package for step definitions
        plugin = {"pretty", 
                 "html:target/cucumber-reports/notifications-report.html", 
                 "json:target/cucumber-reports/notifications.json",
                 "junit:target/cucumber-reports/notifications.xml"},
        monochrome = true
)
    
public class notificationAPIRunner extends AbstractTestNGCucumberTests {
    // Default constructor to help TestNG find the class
    public notificationAPIRunner() {
        super();
    }
}