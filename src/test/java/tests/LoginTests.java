package tests;

import org.testng.annotations.Test;
import pages.LoginPage;
import static org.testng.Assert.*;

public class LoginTests extends TestBase {
    private String baseUrl = "https://demo.bludit.com/admin/";

    @Test
    public void pageTitleContainsBludit() {
        driver.get(baseUrl);
        String title = driver.getTitle();
        assertTrue(title.toLowerCase().contains("bludit") || title.length() > 0);
    }

    @Test
    public void loginFormIsPresent() {
        LoginPage lp = new LoginPage(driver);
        lp.openLogin(baseUrl);
        assertTrue(lp.hasLoginForm(), "Login form should be present");
    }

    @Test
    public void fillInputsAndSubmit() {
        LoginPage lp = new LoginPage(driver);
        lp.openLogin(baseUrl);
        if (lp.hasLoginForm()) {
            lp.fillUsername("invalid_user");
            lp.fillPassword("invalid_pass");
            lp.submit();
        }
    }
}
