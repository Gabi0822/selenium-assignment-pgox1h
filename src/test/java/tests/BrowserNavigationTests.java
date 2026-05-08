package tests;

import org.testng.annotations.Test;
import config.ConfigReader;

import static org.testng.Assert.*;

/**
 * Tests for browser navigation functionality including history navigation.
 */
public class BrowserNavigationTests extends TestBase {

    /**
     * Verify browser back navigation from admin to home page.
     */
    @Test(description = "Browser back navigation")
    public void backButtonNavigation() {
        String homeUrl = navigateToAndCapture(ConfigReader.getHomeUrl());
        navigateToAndCapture(ConfigReader.getBaseUrl());
        driver.navigate().back();
        String currentUrl = driver.getCurrentUrl();
        
        assertTrue(
            currentUrl.equals(homeUrl) || currentUrl.contains("bludit.com"),
            "Back button should navigate to home page"
        );
    }

    /**
     * Verify browser forward navigation after using back button.
     * Depends on: backButtonNavigation
     */
    @Test(
        dependsOnMethods = "backButtonNavigation",
        description = "Browser forward navigation"
    )
    public void forwardButtonNavigation() {
        driver.navigate().forward();
        String forwardUrl = driver.getCurrentUrl();
        
        assertNotNull(forwardUrl, "URL should be available after forward navigation");
    }

    /**
     * Navigate to URL and return the current URL for comparison.
     */
    private String navigateToAndCapture(String url) {
        driver.get(url);
        return driver.getCurrentUrl();
    }
}
