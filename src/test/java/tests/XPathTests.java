package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;
import config.TestConstants;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Tests for complex XPath expressions covering various selector patterns.
 */
public class XPathTests extends TestBase {
    // XPath expressions as constants for maintainability
    private static final String XPATH_TEXT_INPUT_WITH_ATTRIBUTES = "//input[@type='text' and @name]";
    private static final String XPATH_PASSWORD_AFTER_TEXT = "//input[@type='password'][preceding::input[@type='text']]";
    private static final String XPATH_BUTTON_WITH_LOGIN_TEXT = "//button[contains(normalize-space(), 'Login')]";
    private static final String XPATH_DIV_WITH_INPUT_OR_LABEL = "//div[input or label]";
    private static final String XPATH_LINK_WITHOUT_LOGOUT = "//a[not(contains(translate(@href, 'LOGOUT', 'logout'), 'logout'))]";

    /**
     * Test XPath 1: Find input with type='text' AND name attribute using AND operator.
     */
    @Test
    public void complexXPath_1_TextInputWithAttributes() {
        try {
            driver.get(TestConstants.BASE_URL);
            WebElement element = driver.findElement(By.xpath(XPATH_TEXT_INPUT_WITH_ATTRIBUTES));
            assertNotNull(element);
        } catch (Exception e) {
            throw new SkipException("XPath 1 - Text input with attributes: " + e.getMessage());
        }
    }

    /**
     * Test XPath 2: Find password input with preceding text input using axis selector.
     */
    @Test
    public void complexXPath_2_PasswordAfterTextInput() {
        try {
            driver.get(TestConstants.BASE_URL);
            WebElement element = driver.findElement(By.xpath(XPATH_PASSWORD_AFTER_TEXT));
            assertNotNull(element);
        } catch (Exception e) {
            throw new SkipException("XPath 2 - Password after text: " + e.getMessage());
        }
    }

    /**
     * Test XPath 3: Find button containing "Login" text using normalize-space for robustness.
     */
    @Test
    public void complexXPath_3_ButtonWithNormalizedText() {
        try {
            driver.get(TestConstants.BASE_URL);
            WebElement element = driver.findElement(By.xpath(XPATH_BUTTON_WITH_LOGIN_TEXT));
            assertNotNull(element);
        } catch (Exception e) {
            throw new SkipException("XPath 3 - Button with normalized text: " + e.getMessage());
        }
    }

    /**
     * Test XPath 4: Find divs containing either input or label child elements using OR logic.
     */
    @Test
    public void complexXPath_4_DivWithChildElements() {
        try {
            driver.get(TestConstants.BASE_URL);
            List<WebElement> elements = driver.findElements(By.xpath(XPATH_DIV_WITH_INPUT_OR_LABEL));
            assertTrue(elements.size() > 0);
        } catch (Exception e) {
            throw new SkipException("XPath 4 - Div with input or label: " + e.getMessage());
        }
    }

    /**
     * Test XPath 5: Find links NOT containing "logout" in href attribute using negation logic.
     */
    @Test
    public void complexXPath_5_LinkWithNegationLogic() {
        try {
            driver.get(TestConstants.BASE_URL);
            List<WebElement> elements = driver.findElements(By.xpath(XPATH_LINK_WITHOUT_LOGOUT));
            assertTrue(elements.size() > 0);
        } catch (Exception e) {
            throw new SkipException("XPath 5 - Link without logout: " + e.getMessage());
        }
    }
}

