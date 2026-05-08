package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for login functionality.
 */
public class LoginPage extends BasePage {
    
    // Locators using stable IDs instead of generic classes
    private final By usernameInput = By.id("jsusername");
    private final By passwordInput = By.id("jspassword");
    private final By submitButton = By.cssSelector("button[type='submit'][name='save']");

    /**
     * Initialize LoginPage with WebDriver instance.
     */
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
     * Check if login form is present on the page.
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
     * Fill username field with provided text.
     */
    public void enterUsername(String username) {
        waitForElement(usernameInput).clear();
        waitForElement(usernameInput).sendKeys(username);
    }

    /**
     * Fill password field with provided text.
     */
    public void enterPassword(String password) {
        waitForElement(passwordInput).clear();
        waitForElement(passwordInput).sendKeys(password);
    }

    /**
     * Submit login form by clicking submit button or pressing Enter.
     */
    public void clickLoginButton() throws Exception {
        try {
            waitForElement(submitButton).click();
        } catch (Exception e) {
            // Fallback: submit via password field
            waitForElement(passwordInput).submit();
        }
    }
}
