package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import config.ConfigReader;

/**
 * Factory class for creating WebDriver instances with proper configuration.
 * 
 * Configuration is loaded from external properties file via ConfigReader, ensuring:
 * - No hardcoded browser settings
 * - Easy maintenance and updates
 * - Consistent driver setup across all tests
 * - Container-friendly options (headless, no-sandbox, etc.)
 */
public class DriverFactory {

    /**
     * Create a Chrome WebDriver with headless mode and container-friendly options.
     * Settings are read from test.properties file via ConfigReader.
     *
     * @return configured ChromeDriver instance ready for use
     */
    public static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(createChromeOptions());
    }

    /**
     * Configure Chrome options for headless testing in container environment.
     * All settings are configurable via external properties file:
     * - browser.headless: Run without visible UI
     * - --no-sandbox: For container compatibility
     * - --disable-dev-shm-usage: Avoid shared memory issues
     *
     * @return ChromeOptions with headless, sandbox, and shared memory settings
     */
    private static ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        
        if (ConfigReader.isHeadlessBrowser()) {
            options.addArguments("--headless=new");
        }
        
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return options;
    }
}
