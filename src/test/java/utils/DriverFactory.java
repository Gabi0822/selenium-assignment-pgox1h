package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import config.ConfigReader;

/**
 * Factory class for creating WebDriver instances with proper configuration.
 */
public class DriverFactory {

    /**
     * Create a Chrome WebDriver with headless mode and container-friendly options.
     * Settings are read from test.properties file via ConfigReader.
     */
    public static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(createChromeOptions());
    }

    /**
     * Configure Chrome options for headless testing in container environment.
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
