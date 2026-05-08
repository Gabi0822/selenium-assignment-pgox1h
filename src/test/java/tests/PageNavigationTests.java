package tests;

import org.testng.annotations.Test;
import config.ConfigReader;

import static org.testng.Assert.*;

/**
 * Tests for multi-page navigation and title verification.
 */
public class PageNavigationTests extends TestBase {

    private static final String[] PAGES_TO_VERIFY = {
        ConfigReader.getHomeUrl(),
        ConfigReader.getBaseUrl(),
        ConfigReader.getSettingsUrl()
    };

    /**
     * Verify that page titles load correctly for multiple URLs.
     */
    @Test(description = "Verify page titles load for multiple URLs")
    public void multiplePages_TitlesLoadCorrectly() {
        for (String url : PAGES_TO_VERIFY) {
            driver.get(url);
            String title = driver.getTitle();
            assertNotNull(title, "Title should be present for " + url);
            assertTrue(title.length() > 0, "Title should not be empty for " + url);
        }
    }
}
