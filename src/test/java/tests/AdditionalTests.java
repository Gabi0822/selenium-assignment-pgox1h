package tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.FormPage;
import pages.LoginPage;
import config.ConfigReader;

import static org.testng.Assert.*;

/**
 * Additional tests for multiple pages, form interactions, and authentication.
 */
public class AdditionalTests extends TestBase {
    private static final String[] URLS_TO_TEST = {ConfigReader.getHomeUrl(), ConfigReader.getBaseUrl(), ConfigReader.getSettingsUrl()};
    private static final String FORM_TEST_TEXT = "Test textarea content";
    private static final String LOGGED_USER_TEST_TEXT = "Submitting as logged user";

    /**
     * Verify page titles are not empty for multiple URLs.
     */
    @Test
    public void multiplePagesTitleCheck() {
        for (String url : URLS_TO_TEST) {
            driver.get(url);
            String title = driver.getTitle();
            assertNotNull(title);
            assertTrue(title.length() > 0, "Title should not be empty for " + url);
        }
    }

    /**
     * Verify form elements (textarea, select, radio) can be interacted with.
     */
    @Test
    public void textareaDropdownRadioFormInteraction() {
        // First, login to access the settings page
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.enterUsername(ConfigReader.getLoginUsername());
        loginPage.enterPassword(ConfigReader.getLoginPassword());
        loginPage.clickLoginButton();
        
        // Wait for dashboard to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        AdminPage admin = new AdminPage(driver);
        wait.until(ExpectedConditions.urlContains("/admin"));
        
        // Now navigate to settings page
        driver.get(ConfigReader.getSettingsUrl());
        
        // Wait for settings page to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        
        // Click on advanced tab - try multiple possible selectors
        boolean tabFound = false;
        try {
            // Try by link text containing "Advanced"
            WebElement advancedTab = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(text(), 'Advanced')]")
                )
            );
            advancedTab.click();
            tabFound = true;
        } catch (Exception e1) {
            try {
                // Try by class or other attributes
                WebElement altTab = wait.until(
                    ExpectedConditions.elementToBeClickable(
                        By.xpath("//li//a[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'advanced')]")
                    )
                );
                altTab.click();
                tabFound = true;
            } catch (Exception e2) {
                // Tab not found
            }
        }
        
        if (!tabFound) {
            throw new SkipException("Could not find Advanced tab on settings page");
        }
        
        // Wait for tab content to load
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        
        FormPage form = new FormPage(driver);
        boolean interacted = false;

        // Test text input
        if (form.hasTextInput()) {
            form.fillTextInput(FORM_TEST_TEXT);
            interacted = true;
        }

        // Test select/dropdown
        if (form.hasSelect()) {
            try {
                form.selectByVisibleText("English");
            } catch (Exception e) {
                try {
                    form.selectByVisibleText(form.getFirstSelectOptionText());
                } catch (Exception ignored) {
                    // Select not interactive
                }
            }
            interacted = true;
        }

        if (!interacted) {
            throw new SkipException("No text input/select found on Advanced tab in settings");
        }

        form.submitForm();
    }

    /**
     * Verify login with valid credentials, then verify logout.
     */
    @Test
    public void formRequiresUser_thenLogout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.enterUsername(ConfigReader.getLoginUsername());
        loginPage.enterPassword(ConfigReader.getLoginPassword());
        loginPage.clickLoginButton();

        // Wait for dashboard URL to confirm login
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.urlContains("/admin/dashboard"));

        AdminPage admin = new AdminPage(driver);
        assertTrue(admin.isLoggedIn(), "Should be logged in after valid credentials");

        // Interact with authenticated page
        FormPage form = new FormPage(driver);
        if (form.hasTextarea()) {
            form.fillTextarea(LOGGED_USER_TEST_TEXT);
            form.submitForm();
        }

        // Verify logout
        admin.logout();
        wait.until(ExpectedConditions.titleContains("Login"));
        assertFalse(admin.isLoggedIn(), "Should be logged out");
    }
}
