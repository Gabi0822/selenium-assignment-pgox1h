package tests;

import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.FormPage;
import pages.LoginPage;

import static org.testng.Assert.*;

public class AdditionalTests extends TestBase {
    private String baseUrl = "https://demo.bludit.com/admin/";

    @Test
    public void multiplePagesTitleCheck() {
        String[] urls = new String[]{"https://demo.bludit.com/", "https://demo.bludit.com/admin/"};
        for (String u : urls) {
            driver.get(u);
            String t = driver.getTitle();
            assertNotNull(t);
            assertTrue(t.length() > 0, "Title should not be empty for " + u);
        }
    }

    @Test
    public void textareaDropdownRadioFormInteraction() {
        driver.get(baseUrl);
        FormPage fp = new FormPage(driver);
        boolean interacted = false;

        if (fp.hasTextarea()) {
            fp.fillTextarea("Test textarea content");
            interacted = true;
        }

        if (fp.hasSelect()) {
            try {
                fp.selectByVisibleText("English");
            } catch (Exception e) {
                // fallback to first option
                fp.selectByVisibleText(fp.getFirstSelectOptionText());
            }
            interacted = true;
        }

        if (fp.hasRadio()) {
            fp.selectRadioByIndex(0);
            interacted = true;
        }

        if (!interacted) {
            throw new SkipException("No textarea/select/radio found to interact with on " + baseUrl);
        }

        fp.submitForm();
    }

    @Test
    public void formRequiresUser_thenLogout() {
        // Use system properties if provided, otherwise default to assignment credentials
        String user = System.getProperty("bludit.user", "admin");
        String pass = System.getProperty("bludit.pass", "demo123");

        LoginPage lp = new LoginPage(driver);
        lp.openLogin(baseUrl);
        lp.fillUsername(user);
        lp.fillPassword(pass);
        lp.submit();

        AdminPage ap = new AdminPage(driver);
        assertTrue(ap.isLoggedIn(), "Should be logged in after valid credentials");

        // try to reach a page that requires authentication
        driver.get("https://demo.bludit.com/admin/dashboard/");
        FormPage fp = new FormPage(driver);
        if (fp.hasTextarea()) {
            fp.fillTextarea("Submitting as logged user");
            fp.submitForm();
        }

        // logout
        ap.logout();
        assertFalse(ap.isLoggedIn(), "Should be logged out");
    }
}
