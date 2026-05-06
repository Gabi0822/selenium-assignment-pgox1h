package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FormPage extends BasePage {
    private By textarea = By.tagName("textarea");
    private By select = By.tagName("select");
    private By radio = By.cssSelector("input[type='radio']");
    private By submit = By.cssSelector("button[type='submit'], input[type='submit']");

    public FormPage(WebDriver driver) {
        super(driver);
    }

    public boolean hasTextarea() {
        try {
            waitForVisible(textarea);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void fillTextarea(String text) {
        WebElement t = waitForVisible(textarea);
        t.clear();
        t.sendKeys(text);
    }

    public boolean hasSelect() {
        try {
            waitForVisible(select);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectByVisibleText(String visible) {
        WebElement s = waitForVisible(select);
        Select sel = new Select(s);
        sel.selectByVisibleText(visible);
    }

    public String getFirstSelectOptionText() {
        WebElement s = waitForVisible(select);
        Select sel = new Select(s);
        return sel.getOptions().get(0).getText();
    }

    public boolean hasRadio() {
        try {
            waitForVisible(radio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectRadioByIndex(int index) {
        java.util.List<WebElement> radios = driver.findElements(radio);
        if (radios.size() > index) {
            radios.get(index).click();
        }
    }

    public void submitForm() {
        try {
            waitForVisible(submit).click();
        } catch (Exception e) {
            // ignore
        }
    }
}
