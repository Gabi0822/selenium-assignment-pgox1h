package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Page Object for form interactions including textarea, select, and radio buttons.
 */
public class FormPage extends BasePage {
    private final By textareaElement = By.tagName("textarea");
    private final By selectElement = By.tagName("select");
    private final By radioElement = By.cssSelector("input[type='radio']");
    private final By submitButton = By.cssSelector("button[type='submit'], input[type='submit']");

    public FormPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Check if textarea is present on page.
     */
    public boolean hasTextarea() {
        try {
            waitForElement(textareaElement);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Fill textarea with text.
     */
    public void fillTextarea(String text) {
        WebElement textarea = waitForElement(textareaElement);
        textarea.clear();
        textarea.sendKeys(text);
    }

    /**
     * Check if select dropdown is present.
     */
    public boolean hasSelect() {
        try {
            waitForElement(selectElement);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Select option by visible text.
     */
    public void selectByVisibleText(String visible) {
        WebElement select = waitForElement(selectElement);
        new Select(select).selectByVisibleText(visible);
    }

    /**
     * Get first option text from dropdown.
     */
    public String getFirstSelectOptionText() {
        WebElement select = waitForElement(selectElement);
        return new Select(select).getOptions().get(0).getText();
    }

    /**
     * Check if radio button is present.
     */
    public boolean hasRadio() {
        try {
            waitForElement(radioElement);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Select radio button by index.
     */
    public void selectRadioByIndex(int index) {
        List<WebElement> radios = driver.findElements(radioElement);
        if (radios.size() > index) {
            radios.get(index).click();
        }
    }

    /**
     * Submit the form.
     */
    public void submitForm() {
        try {
            waitForElement(submitButton).click();
        } catch (Exception e) {
            // Ignore if submit button not found
        }
    }
}
