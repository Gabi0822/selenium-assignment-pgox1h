package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage extends BasePage {
    private By logoutLink = By.cssSelector("a.logout, a[href*='logout']");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoggedIn() {
        try {
            waitForVisible(logoutLink);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        try {
            waitForVisible(logoutLink).click();
        } catch (Exception ignored) {
        }
    }
}
