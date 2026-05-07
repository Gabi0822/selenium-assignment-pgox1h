package listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * TestNG listener that automatically captures screenshots when a test fails.
 * Screenshots are saved to build/screenshots/ directory with timestamp.
 */
public class ScreenshotOnFailureListener implements ITestListener {

    /**
     * Called when a test fails.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = extractWebDriver(result.getInstance());

        if (driver != null) {
            captureScreenshot(driver, result.getName());
        }
    }

    /**
     * Extract WebDriver from test instance using reflection.
     * Tries to find a field named 'driver' of type WebDriver.
     */
    private WebDriver extractWebDriver(Object testInstance) {
        if (testInstance == null) {
            return null;
        }

        try {
            // Try to find 'driver' field in the test class or its parent classes
            Class<?> currentClass = testInstance.getClass();
            while (currentClass != null && !currentClass.equals(Object.class)) {
                try {
                    java.lang.reflect.Field driverField = currentClass.getDeclaredField("driver");
                    driverField.setAccessible(true);
                    Object driverObj = driverField.get(testInstance);
                    
                    if (driverObj instanceof WebDriver) {
                        return (WebDriver) driverObj;
                    }
                } catch (NoSuchFieldException e) {
                    // Field not found in this class, try parent
                }
                
                currentClass = currentClass.getSuperclass();
            }
        } catch (Exception e) {
            System.out.println("Error extracting WebDriver: " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Capture and save screenshot.
     */
    private void captureScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Create screenshots directory
            String screenshotsDir = "build/screenshots";
            File dir = new File(screenshotsDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Generate filename with timestamp
            String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS"));
            String filename = screenshotsDir + "/" + testName + "_" + timestamp + ".png";

            // Copy screenshot file
            File destFile = new File(filename);
            copyFile(screenshotFile, destFile);

            System.out.println("Screenshot captured: " + filename);
        } catch (Exception e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Copy file from source to destination.
     */
    private void copyFile(File source, File dest) throws IOException {
        byte[] buffer = new byte[1024];
        FileOutputStream fos = null;
        java.io.FileInputStream fis = null;
        try {
            fis = new java.io.FileInputStream(source);
            fos = new FileOutputStream(dest);
            int len;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } finally {
            if (fis != null) fis.close();
            if (fos != null) fos.close();
        }
    }
}
