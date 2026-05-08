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
    private final By textInputElement = By.cssSelector("input[type='text']");
    private final By selectElement = By.tagName("select");
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
     * Get all textareas on the page.
     */
    public List<WebElement> getAllTextareas() {
        return driver.findElements(textareaElement);
    }

    /**
     * Fill the last textarea on page (typically the custom field JSON Format).
     */
    public void fillLastTextarea(String text) throws InterruptedException {
        List<WebElement> textareas = getAllTextareas();
        if (textareas.size() > 0) {
            WebElement lastTextarea = textareas.get(textareas.size() - 1);
            
            // Scroll into view
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lastTextarea);
            Thread.sleep(500);
            
            lastTextarea.clear();
            lastTextarea.sendKeys(text);
            return;
        }
        
        // Fallback: use standard method
        fillTextarea(text);
    }

    /**
     * Check if text input is present on page.
     */
    public boolean hasTextInput() throws InterruptedException {
        java.util.List<WebElement> allInputs = driver.findElements(By.cssSelector("input[type='text']"));
        
        if (allInputs.size() == 0) {
            return false;
        }
        
        // Scroll and find visible input
        WebElement firstInput = allInputs.get(0);
        
        // Scroll into view
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstInput);
        Thread.sleep(500);
        
        // Inputs exist, return true
        return true;
    }

    /**
     * Fill text input with text.
     */
    public void fillTextInput(String text) throws InterruptedException {
        java.util.List<WebElement> inputs = driver.findElements(By.cssSelector("input[type='text']"));
        if (inputs.size() > 0) {
            WebElement input = inputs.get(0);
            
            // Scroll into view
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
            Thread.sleep(500);
            
            // Use JavaScript to fill
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change'));",
                input, text
            );
            return;
        }
        
        WebElement input = waitForElement(textInputElement);
        input.clear();
        input.sendKeys(text);
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
