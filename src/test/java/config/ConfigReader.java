package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration reader that loads test settings from external properties file.
 */
public class ConfigReader {
    private static Properties properties;

    static {
        loadProperties();
    }

    /**
     * Load properties from test.properties file.
     */
    private static void loadProperties() {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("test.properties")) {

            properties = new Properties();
            if (input == null) {
                throw new RuntimeException("test.properties file not found in resources");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test.properties: " + e.getMessage());
        }
    }

    /**
     * Get property value by key.
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    /**
     * Get property with default value if not found.
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get property as integer.
     */
    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    /**
     * Get property as boolean.
     */
    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    // Convenience methods for common properties
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getHomeUrl() {
        return getProperty("home.url");
    }

    public static String getDashboardUrl() {
        return getProperty("dashboard.url");
    }

    public static String getLogoutUrl() {
        return getProperty("logout.url");
    }

    public static String getSettingsUrl() {
        return getProperty("settings.url");
    }

    public static String getLoginUsername() {
        return getProperty("login.username");
    }

    public static String getLoginPassword() {
        return getProperty("login.password");
    }

    public static int getExplicitWaitTimeout() {
        return getIntProperty("explicit.wait.timeout");
    }

    public static int getShortWaitTimeout() {
        return getIntProperty("short.wait.timeout");
    }

    public static String getBrowserName() {
        return getProperty("browser.name");
    }

    public static boolean isHeadlessBrowser() {
        return getBooleanProperty("browser.headless");
    }

    public static boolean shouldTakeScreenshotOnFailure() {
        return getBooleanProperty("screenshot.on.failure");
    }

    public static String getScreenshotDirectory() {
        return getProperty("screenshot.directory");
    }
}
