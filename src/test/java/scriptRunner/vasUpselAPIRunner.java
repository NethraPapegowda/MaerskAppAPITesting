package scriptRunner;

import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@Test 
@CucumberOptions(
        features = "src/test/resources/features/vasUpsell.feature", // Path to your feature files
        glue = {"com.maersk.definations"}, // Package for step definitions
        plugin = {"pretty", 
                 "html:target/cucumber-reports/vasUpsell-report.html", 
                 "json:target/cucumber-reports/vasUpsell.json",
                 "junit:target/cucumber-reports/vasUpsell.xml"},
        monochrome = true
)
    
public class vasUpselAPIRunner extends AbstractTestNGCucumberTests {
    // Default constructor to help TestNG find the class
    public vasUpselAPIRunner() {
        super();
    }
}
    
