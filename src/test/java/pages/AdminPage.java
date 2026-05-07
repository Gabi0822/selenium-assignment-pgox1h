package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import config.TestConstants;

/**
 * Page Object for admin dashboard functionality.
 */
public class AdminPage extends BasePage {
    private final By logoutLink = By.xpath("//a[@href='/admin/logout' or contains(normalize-space(), 'Log out')]");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Check if user is logged in.
     */
    public boolean isLoggedIn() {
        return isDashboardUrlDisplayed() || isLogoutLinkPresent();
    }

    /**
     * Check if dashboard URL is in current URL.
     */
    private boolean isDashboardUrlDisplayed() {
        return driver.getCurrentUrl().contains("/admin/dashboard");
    }

    /**
     * Check if logout link is visible.
     */
    private boolean isLogoutLinkPresent() {
        try {
            return !driver.findElements(logoutLink).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Logout from application.
     */
    public void logout() {
        try {
            waitForElement(logoutLink).click();
        } catch (Exception ignored) {
            navigateTo(TestConstants.LOGOUT_URL);
        }
    }
}
