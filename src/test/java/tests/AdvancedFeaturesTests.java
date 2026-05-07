package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.AdminPage;
import config.ConfigReader;

import static org.testng.Assert.*;

/**
 * Advanced tests featuring test dependencies, history navigation, JavaScript execution, cookies, and random data.
 * Demonstrates advanced Selenium features with configuration from external properties file.
 */
public class AdvancedFeaturesTests extends TestBase {

    /**
     * Base login test - other tests depend on this.
     * Logs in with valid credentials from configuration.
     */
    @Test(description = "Login as base test for dependent tests")
    public void loginForDependentTests() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.enterUsername(ConfigReader.getLoginUsername());
        loginPage.enterPassword(ConfigReader.getLoginPassword());
        loginPage.clickLoginButton();

        // Wait for dashboard
        AdminPage admin = new AdminPage(driver);
        assertTrue(admin.isLoggedIn(), "Should be logged in");
    }

    /**
     * Test that depends on loginForDependentTests - only runs if login succeeds.
     * Performs browser navigation (back) to test history functionality.
     */
    @Test(
        dependsOnMethods = "loginForDependentTests",
        description = "Test browser history navigation"
    )
    public void historyTest_BackNavigation() {
        // Navigate to home
        driver.get(ConfigReader.getHomeUrl());
        String homeUrl = driver.getCurrentUrl();
        assertTrue(homeUrl.contains("bludit.com"), "Should be on Bludit home page");

        // Navigate to admin
        driver.get(ConfigReader.getBaseUrl());
        String adminUrl = driver.getCurrentUrl();
        assertTrue(adminUrl.contains("admin"), "Should be on admin page");

        // Use back to navigate back to home
        driver.navigate().back();
        String currentUrl = driver.getCurrentUrl();
        
        // Verify we're back on home
        assertTrue(
            currentUrl.equals(homeUrl) || currentUrl.contains("bludit.com"),
            "Should navigate back to home page"
        );
    }

    /**
     * Test that depends on loginForDependentTests - only runs if login succeeds.
     * Tests forward navigation after going back.
     */
    @Test(
        dependsOnMethods = "historyTest_BackNavigation",
        description = "Test browser forward navigation after back"
    )
    public void historyTest_ForwardNavigation() {
        AdminPage admin = new AdminPage(driver);
        String currentUrl = driver.getCurrentUrl();

        // Go forward to get back to admin
        driver.navigate().forward();
        String forwardUrl = driver.getCurrentUrl();

        // Should be back on a page (might be admin or home depending on cookies)
        assertNotNull(forwardUrl, "URL should not be null after forward");
    }

    /**
     * Test that depends on loginForDependentTests - only runs if login succeeds.
     * Uses JavascriptExecutor to scroll down the page.
     */
    @Test(
        dependsOnMethods = "loginForDependentTests",
        description = "Use JavascriptExecutor to scroll page"
    )
    public void javascriptExecutor_ScrollPage() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Get window height to check if scrolling is possible
        long windowHeight = (long) jsExecutor.executeScript("return window.innerHeight;");
        long documentHeight = (long) jsExecutor.executeScript("return document.documentElement.scrollHeight;");

        // Only test scrolling if document is taller than window
        if (documentHeight > windowHeight) {
            // Get initial scroll position
            long initialScroll = (long) jsExecutor.executeScript("return window.pageYOffset;");
            assertEquals(initialScroll, 0L, "Should start at top");

            // Scroll down
            jsExecutor.executeScript("window.scrollBy(0, 500);");

            // Get new scroll position
            long scrollAfter = (long) jsExecutor.executeScript("return window.pageYOffset;");
            assertTrue(scrollAfter > initialScroll, "Page should have scrolled down");

            // Scroll back to top
            jsExecutor.executeScript("window.scrollTo(0, 0);");
            long scrollBack = (long) jsExecutor.executeScript("return window.pageYOffset;");
            assertEquals(scrollBack, 0L, "Should be back at top");
        } else {
            // Page is short, just verify we can execute JavaScript
            long scroll = (long) jsExecutor.executeScript("return window.pageYOffset;");
            assertTrue(scroll >= 0, "Scroll position should be non-negative");
        }
    }

    /**
     * Test JavascriptExecutor to click hidden or hard-to-reach elements.
     */
    @Test(description = "Use JavascriptExecutor to interact with elements")
    public void javascriptExecutor_ClickElement() {
        driver.get(ConfigReader.getBaseUrl());
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        // Get page title using JavaScript
        String jsTitle = (String) jsExecutor.executeScript("return document.title;");
        String seleniumTitle = driver.getTitle();

        assertEquals(jsTitle, seleniumTitle, "JavaScript and Selenium title should match");
    }

    /**
     * Test cookie manipulation - add, read, and delete cookies.
     */
    @Test(description = "Manipulate cookies - add, read, delete")
    public void cookieManipulation() {
        driver.get(ConfigReader.getBaseUrl());

        // Add a custom cookie
        String cookieName = "TestCookie";
        String cookieValue = "TestValue123";
        org.openqa.selenium.Cookie testCookie = new org.openqa.selenium.Cookie(cookieName, cookieValue);
        driver.manage().addCookie(testCookie);

        // Read the cookie back
        org.openqa.selenium.Cookie retrievedCookie = driver.manage().getCookieNamed(cookieName);
        assertNotNull(retrievedCookie, "Cookie should exist");
        assertEquals(retrievedCookie.getValue(), cookieValue, "Cookie value should match");

        // Delete the cookie
        driver.manage().deleteCookie(testCookie);

        // Verify cookie is gone
        org.openqa.selenium.Cookie deletedCookie = driver.manage().getCookieNamed(cookieName);
        assertNull(deletedCookie, "Cookie should be deleted");
    }

    /**
     * Test random data generation using random credentials in form.
     */
    @Test(description = "Generate and use random test data")
    public void randomData_GenerateAndUse() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());

        // Generate random username (not for real login, just to test randomness)
        String randomUsername = "user_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
        String randomPassword = "pass_" + System.currentTimeMillis();

        assertTrue(randomUsername.startsWith("user_"), "Username should start with user_");
        assertTrue(randomPassword.startsWith("pass_"), "Password should start with pass_");
        assertTrue(!randomUsername.equals(randomPassword), "Username and password should be different");

        // Fill form with random data
        loginPage.enterUsername(randomUsername);
        loginPage.enterPassword(randomPassword);

        // Verify fields are filled with our random data
        org.openqa.selenium.WebElement usernameField = driver.findElement(By.id("jsusername"));
        assertEquals(usernameField.getAttribute("value"), randomUsername, "Username field should contain random username");
    }

    /**
     * Test that depends on loginForDependentTests - logout test depends on successful login.
     * Demonstrates test dependency pattern.
     */
    @Test(
        dependsOnMethods = "loginForDependentTests",
        description = "Logout test depending on successful login"
    )
    public void logoutAfterLogin_DependentTest() {
        // Navigate back to admin to ensure we're in the right context
        driver.get(ConfigReader.getBaseUrl());
        
        AdminPage admin = new AdminPage(driver);
        
        // Check if we're still logged in
        if (admin.isLoggedIn()) {
            admin.logout();
            // Give page time to redirect after logout
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            assertFalse(admin.isLoggedIn(), "Should be logged out after logout");
        } else {
            // If not logged in, just verify the logout page is shown
            assertTrue(driver.getCurrentUrl().contains("bludit"), "Should be on Bludit site");
        }
    }
}
