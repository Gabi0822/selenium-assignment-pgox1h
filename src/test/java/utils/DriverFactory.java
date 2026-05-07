package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Factory class for creating WebDriver instances with proper configuration.
 */
public class DriverFactory {

    /**
     * Create a Chrome WebDriver with headless mode and container-friendly options.
     *
     * @return configured ChromeDriver instance
     */
    public static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(createChromeOptions());
    }

    /**
     * Configure Chrome options for headless testing in container environment.
     *
     * @return ChromeOptions with headless, sandbox, and shared memory settings
     */
    private static ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        return options;
    }
}
