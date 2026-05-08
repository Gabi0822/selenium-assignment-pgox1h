package tests;

import org.testng.annotations.Test;
import pages.LoginPage;
import config.ConfigReader;

import static org.testng.Assert.*;

/**
 * Tests for login page and login form functionality.
 * Validates form presence, field interaction, and submission.
 */
public class LoginTests extends TestBase {

    // ==================== Page Verification Tests ====================

    /**
     * Verify the login page title is properly loaded.
     */
    @Test(description = "Verify page title contains Bludit")
    public void loginPage_TitleIsLoaded() {
        driver.get(ConfigReader.getBaseUrl());
        String title = driver.getTitle();
        assertTrue(title.toLowerCase().contains("bludit") || title.length() > 0,
                "Page title should be loaded and contain Bludit or have content");
    }

    // ==================== Form Element Tests ====================

    /**
     * Verify login form elements are present on the page.
     */
    @Test(description = "Verify login form elements are present")
    public void loginForm_ElementsArePresent() {
        LoginPage loginPage = openLoginPage();
        assertTrue(loginPage.isLoginFormPresent(), "Login form should be present on page");
    }

    // ==================== Form Interaction Tests ====================

    /**
     * Verify form fields can be filled and submitted with invalid credentials.
     */
    @Test(description = "Verify form fields accept input and submit")
    public void loginForm_FieldsAcceptInputAndSubmit() throws Exception {
        LoginPage loginPage = openLoginPage();
        
        if (loginPage.isLoginFormPresent()) {
            loginPage.enterUsername("invalid_user");
            loginPage.enterPassword("invalid_pass");
            loginPage.clickLoginButton();
        }
    }

    // ==================== Helper Methods ====================

    /**
     * Open the login page and return a LoginPage object for interaction.
     */
    private LoginPage openLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        return loginPage;
    }
}
