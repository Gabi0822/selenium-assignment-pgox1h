package tests;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Tests for random test data generation functionality.
 */
public class TestDataGenerationTests extends TestBase {

    /**
     * Verify random credentials generation.
     */
    @Test(description = "Random credentials data generation")
    public void generateRandomCredentials() {
        String randomUsername = generateRandomUsername();
        String randomPassword = generateRandomPassword();
        
        assertNotNull(randomUsername, "Generated username should not be null");
        assertTrue(randomUsername.length() > 0, "Generated username should not be empty");
        assertTrue(randomUsername.startsWith("user_"), "Username should have predictable format");
        
        assertNotNull(randomPassword, "Generated password should not be null");
        assertTrue(randomPassword.length() > 0, "Generated password should not be empty");
        assertTrue(randomPassword.startsWith("pass_"), "Password should have predictable format");
        
        assertNotEquals(randomUsername, randomPassword, "Generated data should be different");
    }

    /**
     * Generate random username with timestamp.
     */
    private String generateRandomUsername() {
        return "user_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
    }

    /**
     * Generate random password with timestamp.
     */
    private String generateRandomPassword() {
        return "pass_" + System.currentTimeMillis();
    }
}
