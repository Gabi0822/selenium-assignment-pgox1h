package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import config.ConfigReader;

import java.util.List;

import static org.testng.Assert.*;

/**
 * Tests for complex XPath expressions and element selection strategies.
 * Validates various XPath patterns: attributes, axes, text matching, logical operators, negation.
 */
public class XPathTests extends TestBase {


    private static final String XPATH_TEXT_INPUT_WITH_ATTRIBUTES = "//input[@type='text' and @name]";
    private static final String XPATH_PASSWORD_AFTER_TEXT = "//input[@type='password'][preceding::input[@type='text']]";
    private static final String XPATH_BUTTON_WITH_LOGIN_TEXT = "//button[contains(normalize-space(), 'Login')]";
    private static final String XPATH_DIV_WITH_INPUT_OR_LABEL = "//div[input or label]";
    private static final String XPATH_LINK_WITHOUT_LOGOUT = "//a[not(contains(translate(@href, 'LOGOUT', 'logout'), 'logout'))]";


    /**
     * Test XPath AND operator: find input with type='text' AND name attribute.
     */
    @Test(description = "XPath: AND operator with multiple attributes")
    public void xpath_AndOperator_FindTextInputWithName() throws Exception {
        driver.get(ConfigReader.getBaseUrl());
        WebElement element = driver.findElement(By.xpath(XPATH_TEXT_INPUT_WITH_ATTRIBUTES));
        assertNotNull(element, "Should find text input with name attribute");
    }

    /**
     * Test XPath axes: find password input that has preceding text input (preceding axis).
     */
    @Test(description = "XPath: Axes with preceding:: to find password after text input")
    public void xpath_Axes_PasswordFollowsTextInput() throws Exception {
        driver.get(ConfigReader.getBaseUrl());
        WebElement element = driver.findElement(By.xpath(XPATH_PASSWORD_AFTER_TEXT));
        assertNotNull(element, "Should find password input after text input");
    }

    /**
     * Test XPath text matching: find button with "Login" text using normalize-space for robustness.
     */
    @Test(description = "XPath: Text matching with normalize-space for flexible whitespace")
    public void xpath_TextMatching_FindLoginButton() throws Exception {
        driver.get(ConfigReader.getBaseUrl());
        WebElement element = driver.findElement(By.xpath(XPATH_BUTTON_WITH_LOGIN_TEXT));
        assertNotNull(element, "Should find button with Login text");
    }

    /**
     * Test XPath OR operator: find divs containing either input or label child elements.
     */
    @Test(description = "XPath: OR operator to find divs with input or label children")
    public void xpath_OrOperator_FindDivsWithInputOrLabel() throws Exception {
        driver.get(ConfigReader.getBaseUrl());
        List<WebElement> elements = driver.findElements(By.xpath(XPATH_DIV_WITH_INPUT_OR_LABEL));
        assertTrue(elements.size() > 0, "Should find divs containing input or label elements");
    }

    /**
     * Test XPath negation: find links NOT containing "logout" in href (using not() function).
     */
    @Test(description = "XPath: Negation logic to exclude logout links")
    public void xpath_Negation_FindLinksWithoutLogout() throws Exception {
        driver.get(ConfigReader.getBaseUrl());
        List<WebElement> elements = driver.findElements(By.xpath(XPATH_LINK_WITHOUT_LOGOUT));
        assertTrue(elements.size() > 0, "Should find links that don't contain logout");
    }
}

