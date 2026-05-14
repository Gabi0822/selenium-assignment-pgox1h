package tests;

import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.LoginPage;
import config.ConfigReader;
import utils.DataGenerator;

import static org.testng.Assert.*;

/**
 * Tests for user authentication workflows including login and logout functionality.
 */
public class AuthenticationTests extends TestBase {

    /**
     * Verify login with valid credentials from configuration.
     */
    @Test(
        groups = "authentication",
        description = "Authenticate with valid credentials"
    )
    public void loginWithValidCredentials() throws Exception {
        performLogin();
        
        // Wait for admin page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getIntProperty("explicit.wait.timeout")));
        wait.until(ExpectedConditions.urlContains("/admin"));
        
        AdminPage admin = new AdminPage(driver);
        assertTrue(admin.isLoggedIn(), "Should be logged in with valid credentials");
    }

    /**
     * Verify login fails with random (invalid) credentials.
     * Tests that random generated data cannot be used to authenticate.
     */
    @Test(description = "Login fails with random invalid credentials")
    public void loginWithRandomInvalidCredentials() throws Exception {
        String randomUsername = DataGenerator.generateRandomUsername();
        String randomPassword = DataGenerator.generateRandomPassword();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.enterUsername(randomUsername);
        loginPage.enterPassword(randomPassword);
        loginPage.clickLoginButton();
        
        AdminPage admin = new AdminPage(driver);
        assertFalse(admin.isLoggedIn(), "Should not be logged in with random credentials");
    }

    /**
     * Verify logout after successful login.
     * Depends on: loginWithValidCredentials
     */
    @Test(
        dependsOnMethods = "loginWithValidCredentials",
        description = "Logout after successful login"
    )
    public void logoutAfterLogin() throws Exception {
        driver.get(ConfigReader.getBaseUrl());
        
        AdminPage admin = new AdminPage(driver);
        
        if (admin.isLoggedIn()) {
            admin.logout();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getIntProperty("explicit.wait.timeout")));
            wait.until(ExpectedConditions.titleContains("Login"));
            assertFalse(admin.isLoggedIn(), "Should be logged out after logout");
        } else {
            assertTrue(driver.getCurrentUrl().contains("bludit"), "Should be on Bludit site");
        }
    }

    /**
     * Perform login with credentials from configuration.
     */
    private void performLogin() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openLoginPage(ConfigReader.getBaseUrl());
        loginPage.enterUsername(ConfigReader.getLoginUsername());
        loginPage.enterPassword(ConfigReader.getLoginPassword());
        loginPage.clickLoginButton();
        
        // Wait for login to complete
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getIntProperty("explicit.wait.timeout")));
        wait.until(ExpectedConditions.urlContains("/admin"));
    }
}
