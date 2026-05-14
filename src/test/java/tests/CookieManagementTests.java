package tests;

import org.testng.annotations.Test;
import org.openqa.selenium.Cookie;
import config.ConfigReader;
import static org.testng.Assert.*;

/**
 * Tests for cookie management functionality.
 */
public class CookieManagementTests extends TestBase {

    /**
     * Verify cookie add, retrieve, and delete operations.
     */
    @Test(description = "Cookie add, retrieve, and delete operations")
    public void cookieAddRetrieveDelete() {
        driver.get(ConfigReader.getHomeUrl());
        
        Cookie testCookie1 = new Cookie("TestCookie1", "Value1");
        Cookie testCookie2 = new Cookie("TestCookie2", "Value2");
        driver.manage().addCookie(testCookie1);
        driver.manage().addCookie(testCookie2);
        
        Cookie retrievedCookie1 = driver.manage().getCookieNamed("TestCookie1");
        assertNotNull(retrievedCookie1, "TestCookie1 should be found");
        assertEquals(retrievedCookie1.getValue(), "Value1", "Cookie value should match");
        
        driver.manage().deleteCookie(testCookie1);
        Cookie deletedCookie = driver.manage().getCookieNamed("TestCookie1");
        assertNull(deletedCookie, "TestCookie1 should be deleted");
        
        Cookie retrievedCookie2 = driver.manage().getCookieNamed("TestCookie2");
        assertNotNull(retrievedCookie2, "TestCookie2 should still exist");
    }
}
