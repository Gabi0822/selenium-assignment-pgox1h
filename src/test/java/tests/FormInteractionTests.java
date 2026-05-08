package tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.Test;
import pages.FormPage;
import pages.LoginPage;
import config.ConfigReader;

/**
 * Tests for authenticated form interactions on settings page.
 * Focuses on Advanced tab and Custom Field form submissions.
 */

public class FormInteractionTests extends TestBase {

    private static final String FORM_TEST_TEXT = "Test textarea content";
    private static final String CUSTOM_FIELD_JSON = "{\"custom\": \"field\", \"value\": \"Test JSON\"}";
    private static final int SETTINGS_PAGE_LOAD_TIME_MS = 2000;
    private static final int TAB_TRANSITION_TIME_MS = 1500;
    private static final int SHORT_WAIT_TIME_MS = 500;

    /**
     * Verify form interactions on authenticated settings page.
     * Tests Advanced tab form submission and Custom Field textarea interaction.
     */
    @Test(description = "Verify form interactions in Advanced tab and Custom Field section")
    public void settingsPage_FormInteractionWorkflow() throws Exception {
        // Perform login first
        performLogin();
        
        // Wait for authentication to complete
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/admin"));
        
        // Navigate to settings page after login
        driver.get(ConfigReader.getSettingsUrl());
        Thread.sleep(SETTINGS_PAGE_LOAD_TIME_MS);
        
        // Interact with forms in order
        interactWithAdvancedTabForm();
        interactWithCustomFieldForm();
        
        // Submit the form
        FormPage form = new FormPage(driver);
        form.submitForm();
    }

    /**
     * Navigate to settings page and interact with Advanced tab form.
     */
    private void interactWithAdvancedTabForm() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        // Click Advanced tab
        WebElement advancedTab = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(), 'Advanced')]")
            )
        );
        advancedTab.click();
        
        // Wait for tab content to load
        Thread.sleep(TAB_TRANSITION_TIME_MS);
        
        // Fill form elements
        fillAdvancedTabFormFields();
    }

    /**
     * Fill all available form fields in the Advanced tab.
     */
    private void fillAdvancedTabFormFields() throws Exception {
        FormPage form = new FormPage(driver);
        boolean interacted = false;
        
        if (form.hasTextInput()) {
            form.fillTextInput(FORM_TEST_TEXT);
            interacted = true;
        }
        
        if (form.hasSelect()) {
            String optionText = form.getFirstSelectOptionText();
            if (optionText != null && !optionText.trim().isEmpty()) {
                form.selectByVisibleText(optionText);
            }
            interacted = true;
        }
        
        if (!interacted) {
            throw new SkipException("No form elements found on Advanced tab");
        }
    }

    /**
     * Navigate to Custom Field section and fill textarea.
     */
    private void interactWithCustomFieldForm() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        Thread.sleep(SHORT_WAIT_TIME_MS);
        
        // Click Custom Field tab
        WebElement customFieldTab = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'custom')]")
            )
        );
        customFieldTab.click();
        
        // Wait for content to load
        Thread.sleep(TAB_TRANSITION_TIME_MS);
        
        // Fill custom field textarea
        fillCustomFieldTextarea();
    }

    /**
     * Fill the custom field JSON Format textarea.
     */
    private void fillCustomFieldTextarea() throws Exception {
        FormPage form = new FormPage(driver);
        if (form.getAllTextareas().size() > 0) {
            form.fillLastTextarea(CUSTOM_FIELD_JSON);
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
    }
}
