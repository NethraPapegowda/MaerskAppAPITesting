package scriptRunner;

import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@Test 
@CucumberOptions(
        features = "src/test/resources/features/externalLogin.feature", // Path to your feature files
        glue = {"com.maersk.definations"}, // Package for step definitions
        plugin = {"pretty", 
                 "html:target/cucumber-reports/externalLogin-report.html", 
                 "json:target/cucumber-reports/externalLogin.json",
                 "junit:target/cucumber-reports/externalLogin.xml"},
        monochrome = true
)
    
public class externalLoginAPIRunner extends AbstractTestNGCucumberTests {
    // Default constructor to help TestNG find the class
    public externalLoginAPIRunner() {
        super();
    }
}