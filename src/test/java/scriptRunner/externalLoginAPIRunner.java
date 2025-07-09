package scriptRunner;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import util.ConfigManager;

import java.util.Map;

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
    
    @BeforeClass
    public void setup() {
        // Load configuration from YAML file
        ConfigManager.loadConfig("config.yml");
        
        // Get environment specific configuration
        String env = System.getProperty("env", "test"); // Default to test environment
        Map<String, Object> envConfig = ConfigManager.getEnvironmentConfig(env);
        
        if (envConfig != null) {
            System.out.println("Running tests against: " + envConfig.get("baseUrl"));
            
            // You can set system properties or other configurations based on the YAML file
            System.setProperty("api.baseUrl", envConfig.get("baseUrl").toString());
            System.setProperty("api.timeout", envConfig.get("timeout").toString());
        } else {
            System.err.println("Environment configuration not found for: " + env);
        }
    }
}