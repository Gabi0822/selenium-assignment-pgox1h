package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import utils.DriverFactory;
import listeners.ScreenshotOnFailureListener;

/**
 * Base class for all tests providing WebDriver lifecycle management.
 * All test classes should extend this class to ensure proper driver setup and cleanup.
 * Automatically captures screenshots when a test fails.
 */
@Listeners(ScreenshotOnFailureListener.class)
public class TestBase {
    protected WebDriver driver;

    /**
     * Initialize WebDriver before each test method.
     */
    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createChromeDriver();
    }

    /**
     * Clean up WebDriver after each test method.
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
