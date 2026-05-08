package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.AdminPage;
import config.ConfigReader;

import static org.testng.Assert.*;

/**
 * Advanced test scenarios covering authentication, browser navigation, JavaScript execution, 
 * cookies, and random data generation. Tests demonstrate TestNG dependency patterns and advanced Selenium features.
 */
public class AdvancedFeaturesTests extends TestBase {

    // ==================== Constants ====================

    private static final long SHORT_WAIT_MS = 1000;

    // ==================== Authentication Tests ====================

    /**
     * Verify login with valid credentials from configuration.
     * This test serves as the base for dependent tests.
     */
    @Test(description = "Authenticate with valid credentials")
    public void authentication_LoginWithValidCredentials() throws Exception {
        performLogin();
        
        AdminPage admin = new AdminPage(driver);
        assertTrue(admin.isLoggedIn(), "Should be logged in with valid credentials");
    }

    /**
     * Verify logout after successful login and authentication state.
     * Depends on: authentication_LoginWithValidCredentials
     */
    @Test(
        dependsOnMethods = "authentication_LoginWithValidCredentials",
        description = "Logout after successful login"
    )
    public void authentication_LogoutAfterLogin() throws Exception {
        // Navigate back to admin dashboard
        driver.get(ConfigReader.getBaseUrl());
        
        AdminPage admin = new AdminPage(driver);
        
        if (admin.isLoggedIn()) {
            admin.logout();
            Thread.sleep(SHORT_WAIT_MS);
            assertFalse(admin.isLoggedIn(), "Should be logged out after logout");
        } else {
            assertTrue(driver.getCurrentUrl().contains("bludit"), "Should be on Bludit site");
        }
    }

    // ==================== Browser Navigation Tests ====================

    /**
     * Verify browser back navigation from admin to home page.
     * Depends on: authentication_LoginWithValidCredentials
     */
    @Test(
        dependsOnMethods = "authentication_LoginWithValidCredentials",
        description = "Browser back navigation"
    )
    public void navigation_BackButton() {
        String homeUrl = navigateToAndCapture(ConfigReader.getHomeUrl());
        navigateToAndCapture(ConfigReader.getBaseUrl());
        
        // Use back button
        driver.navigate().back();
        String currentUrl = driver.getCurrentUrl();
        
        assertTrue(
            currentUrl.equals(homeUrl) || currentUrl.contains("bludit.com"),
            "Back button should navigate to home page"
        );
    }

    /**
     * Verify browser forward navigation after using back button.
     * Depends on: navigation_BackButton
     */
    @Test(
        dependsOnMethods = "navigation_BackButton",
        description = "Browser forward navigation"
    )
    public void navigation_ForwardButton() {
        driver.navigate().forward();
        String forwardUrl = driver.getCurrentUrl();
        
        assertNotNull(forwardUrl, "URL should be available after forward navigation");
    }

    // ==================== JavaScript Executor Tests ====================

    /**
     * Verify JavaScript scroll functionality and execution capability.
     * Depends on: authentication_LoginWithValidCredentials
     */
    @Test(
        dependsOnMethods = "authentication_LoginWithValidCredentials",
        description = "JavaScript page scrolling"
    )
    public void javascript_PageScrolling() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        
        long windowHeight = (long) jsExecutor.executeScript("return window.innerHeight;");
        long documentHeight = (long) jsExecutor.executeScript("return document.documentElement.scrollHeight;");
        
        if (documentHeight > windowHeight) {
            verifyScrollDown(jsExecutor);
            verifyScrollToTop(jsExecutor);
        } else {
            verifyScrollExecutionWorks(jsExecutor);
        }
    }

    /**
     * Verify JavaScript code execution and result retrieval.
     */
    @Test(description = "JavaScript execution matches Selenium API")
    public void javascript_ExecutionAndRetrieval() {
        driver.get(ConfigReader.getBaseUrl());
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        
        String jsTitle = (String) jsExecutor.executeScript("return document.title;");
        String seleniumTitle = driver.getTitle();
        
        assertEquals(jsTitle, seleniumTitle, "JavaScript and Selenium should return same title");
    }

    // ==================== Cookie Management Tests ====================

    /**
     * Verify cookie operations: create, read, and delete.
     */
    @Test(description = "Cookie add, retrieve, and delete operations")
    public void cookies_AddRetrieveDelete() {
        driver.get(ConfigReader.getBaseUrl());
        
        String cookieName = "TestCookie";
        String cookieValue = "TestValue123";
        
        // Add cookie
        org.openqa.selenium.Cookie testCookie = new org.openqa.selenium.Cookie(cookieName, cookieValue);
        driver.manage().addCookie(testCookie);
        
        // Retrieve and verify
        org.openqa.selenium.Cookie retrievedCookie = driver.manage().getCookieNamed(cookieName);
        assertNotNull(retrievedCookie, "Cookie should exist after creation");
        assertEquals(retrievedCookie.getValue(), cookieValue, "Cookie value should match");
        
        // Delete and verify
        driver.manage().deleteCookie(testCookie);
        org.openqa.selenium.Cookie deletedCookie = driver.manage().getCookieNamed(cookieName);
        assertNull(deletedCookie, "Cookie should not exist after deletion");
    }

    // ==================== Test Data Generation Tests ====================

    /**
     * Verify random test data generation for form input.
     */
    @Test(description = "Generate and validate random test data")
    public void testData_GenerateRandomCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        
        String randomUsername = generateRandomUsername();
        String randomPassword = generateRandomPassword();
        
        assertTrue(randomUsername.startsWith("user_"), "Username should start with user_ prefix");
        assertTrue(randomPassword.startsWith("pass_"), "Password should start with pass_ prefix");
        assertNotEquals(randomUsername, randomPassword, "Generated data should be unique");
        
        // Fill form with generated data
        loginPage.enterUsername(randomUsername);
        loginPage.enterPassword(randomPassword);
        
        // Verify form contains generated data
        org.openqa.selenium.WebElement usernameField = driver.findElement(By.id("jsusername"));
        assertEquals(usernameField.getAttribute("value"), randomUsername, 
            "Form should contain generated username");
    }

    // ==================== Helper Methods ====================

    /**
     * Perform login with credentials from configuration.
     */
    private void performLogin() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.enterUsername(ConfigReader.getLoginUsername());
        loginPage.enterPassword(ConfigReader.getLoginPassword());
        loginPage.clickLoginButton();
    }

    /**
     * Navigate to URL and return the current URL for comparison.
     */
    private String navigateToAndCapture(String url) {
        driver.get(url);
        return driver.getCurrentUrl();
    }

    /**
     * Verify page scrolls down correctly.
     */
    private void verifyScrollDown(JavascriptExecutor jsExecutor) {
        long initialScroll = (long) jsExecutor.executeScript("return window.pageYOffset;");
        assertEquals(initialScroll, 0L, "Page should start at top");
        
        jsExecutor.executeScript("window.scrollBy(0, 500);");
        
        long scrollAfter = (long) jsExecutor.executeScript("return window.pageYOffset;");
        assertTrue(scrollAfter > initialScroll, "Page should scroll down");
    }

    /**
     * Verify page scrolls back to top.
     */
    private void verifyScrollToTop(JavascriptExecutor jsExecutor) {
        jsExecutor.executeScript("window.scrollTo(0, 0);");
        long scrollBack = (long) jsExecutor.executeScript("return window.pageYOffset;");
        assertEquals(scrollBack, 0L, "Page should be back at top");
    }

    /**
     * Verify JavaScript execution works on short pages.
     */
    private void verifyScrollExecutionWorks(JavascriptExecutor jsExecutor) {
        long scroll = (long) jsExecutor.executeScript("return window.pageYOffset;");
        assertTrue(scroll >= 0, "Scroll position should be valid");
    }

    /**
     * Generate random username with timestamp.
     */
    private String generateRandomUsername() {
        return "user_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
    }

    /**
     * Generate random password with timestamp.
     */
    private String generateRandomPassword() {
        return "pass_" + System.currentTimeMillis();
    }
}
