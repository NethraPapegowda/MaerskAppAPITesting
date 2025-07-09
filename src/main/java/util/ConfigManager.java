package util;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

/**
 * Utility class to load and access configuration from YAML files
 */
public class ConfigManager {
    
    private static Map<String, Object> config;
    
    /**
     * Load configuration from a YAML file
     * 
     * @param configFilePath Path to the YAML config file
     */
    @SuppressWarnings("unchecked")
    public static void loadConfig(String configFilePath) {
        try {
            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream(configFilePath);
            config = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            System.err.println("Config file not found: " + configFilePath);
            e.printStackTrace();
        }
    }
    
    /**
     * Get environment-specific configuration
     * 
     * @param env Environment name (test, staging, production)
     * @return Map of configuration properties for the specified environment
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getEnvironmentConfig(String env) {
        if (config == null) {
            loadConfig("config.yml");
        }
        
        Map<String, Object> environments = (Map<String, Object>) config.get("environments");
        return environments != null ? (Map<String, Object>) environments.get(env) : null;
    }
    
    /**
     * Get execution configuration
     * 
     * @return Map of execution configuration properties
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getExecutionConfig() {
        if (config == null) {
            loadConfig("config.yml");
        }
        
        return (Map<String, Object>) config.get("execution");
    }
    
    /**
     * Get reporting configuration
     * 
     * @return Map of reporting configuration properties
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getReportingConfig() {
        if (config == null) {
            loadConfig("config.yml");
        }
        
        return (Map<String, Object>) config.get("reporting");
    }
    
    /**
     * Get a specific configuration value
     * 
     * @param path Dot-separated path to the configuration value (e.g., "environments.test.baseUrl")
     * @return The configuration value, or null if not found
     */
    public static Object getConfigValue(String path) {
        if (config == null) {
            loadConfig("config.yml");
        }
        
        String[] parts = path.split("\\.");
        Map<String, Object> current = config;
        
        for (int i = 0; i < parts.length - 1; i++) {
            Object value = current.get(parts[i]);
            if (!(value instanceof Map)) {
                return null;
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> mapValue = (Map<String, Object>) value;
            current = mapValue;
        }
        
        return current.get(parts[parts.length - 1]);
    }
}
