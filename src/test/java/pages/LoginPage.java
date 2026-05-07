package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for login functionality.
 */
public class LoginPage extends BasePage {
    private final By usernameInput = By.id("jsusername");
    private final By passwordInput = By.id("jspassword");
    private final By submitButton = By.cssSelector("button[type='submit'][name='save']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to login page.
     */
    public void openLoginPage(String loginUrl) {
        navigateTo(loginUrl);
    }

    /**
     * Check if login form is present.
     */
    public boolean isLoginFormPresent() {
        try {
            waitForElement(usernameInput);
            waitForElement(passwordInput);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Fill username field.
     */
    public void enterUsername(String username) {
        waitForElement(usernameInput).clear();
        waitForElement(usernameInput).sendKeys(username);
    }

    /**
     * Fill password field.
     */
    public void enterPassword(String password) {
        waitForElement(passwordInput).clear();
        waitForElement(passwordInput).sendKeys(password);
    }

    /**
     * Submit login form.
     */
    public void clickLoginButton() {
        try {
            waitForElement(submitButton).click();
        } catch (Exception e) {
            waitForElement(passwordInput).submit();
        }
    }
}
