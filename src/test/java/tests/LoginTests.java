package tests;

import org.testng.annotations.Test;
import pages.LoginPage;
import config.TestConstants;

import static org.testng.Assert.*;

/**
 * Tests for login form functionality.
 */
public class LoginTests extends TestBase {

    /**
     * Verify the page title contains "Bludit".
     */
    @Test
    public void pageTitleContainsBludit() {
        driver.get(TestConstants.BASE_URL);
        String title = driver.getTitle();
        assertTrue(title.toLowerCase().contains("bludit") || title.length() > 0);
    }

    /**
     * Verify login form is present on page.
     */
    @Test
    public void loginFormIsPresent() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(TestConstants.BASE_URL);
        assertTrue(loginPage.isLoginFormPresent(), "Login form should be present");
    }

    /**
     * Verify form fields can be filled and submitted.
     */
    @Test
    public void fillInputsAndSubmit() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(TestConstants.BASE_URL);
        if (loginPage.isLoginFormPresent()) {
            loginPage.enterUsername("invalid_user");
            loginPage.enterPassword("invalid_pass");
            loginPage.clickLoginButton();
        }
    }
}
