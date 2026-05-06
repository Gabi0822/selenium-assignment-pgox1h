package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private By username = By.cssSelector("input[type='text'], input[type='email'], input[name='username'], input#username");
    private By password = By.cssSelector("input[type='password']");
    private By submit = By.cssSelector("button[type='submit'], input[type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openLogin(String url) {
        open(url);
    }

    public boolean hasLoginForm() {
        try {
            waitForVisible(username);
            waitForVisible(password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void fillUsername(String u) {
        waitForVisible(username).clear();
        waitForVisible(username).sendKeys(u);
    }

    public void fillPassword(String p) {
        waitForVisible(password).clear();
        waitForVisible(password).sendKeys(p);
    }

    public void submit() {
        try {
            waitForVisible(submit).click();
        } catch (Exception e) {
            driver.findElement(password).submit();
        }
    }
}
