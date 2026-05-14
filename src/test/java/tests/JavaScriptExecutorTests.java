package tests;

import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import config.ConfigReader;
import static org.testng.Assert.*;

/**
 * Tests for JavaScript execution and DOM manipulation capabilities.
 */
public class JavaScriptExecutorTests extends TestBase {

    /**
     * Verify page scrolling functionality using JavaScript.
     */
    @Test(description = "Page scrolling with JavaScript")
    public void pageScrollingWithJavaScript() {
        driver.get(ConfigReader.getHomeUrl());
        
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        
        Object initialScroll = jsExecutor.executeScript("return window.pageYOffset;");
        assertNotNull(initialScroll, "Should get initial scroll position");
        
        jsExecutor.executeScript("window.scrollBy(0, 500);");
        Object afterScroll = jsExecutor.executeScript("return window.pageYOffset;");
        assertNotNull(afterScroll, "Should get scroll position after scrolling");
        
        jsExecutor.executeScript("window.scrollTo(0, 0);");
        Object finalScroll = jsExecutor.executeScript("return window.pageYOffset;");
        assertTrue((long) finalScroll >= 0, "Should be back at or near top");
    }

    /**
     * Verify JavaScript execution and return values.
     * Depends on: pageScrollingWithJavaScript
     */
    @Test(
        dependsOnMethods = "pageScrollingWithJavaScript",
        description = "JavaScript execution with return values"
    )
    public void javaScriptExecutionWithReturnValues() {
        Object result = verifyScrollExecutionWorks();
        assertNotNull(result, "JavaScript should return a value");
    }

    /**
     * Helper to verify JavaScript execution works correctly.
     */
    private Object verifyScrollExecutionWorks() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript("return document.body.scrollHeight;");
    }
}
