package tests;

import org.testng.annotations.Test;
import utils.DataGenerator;

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
        String randomUsername = DataGenerator.generateRandomUsername();
        String randomPassword = DataGenerator.generateRandomPassword();
        
        assertNotNull(randomUsername, "Generated username should not be null");
        assertTrue(randomUsername.length() > 0, "Generated username should not be empty");
        assertTrue(randomUsername.startsWith("user_"), "Username should have predictable format");
        
        assertNotNull(randomPassword, "Generated password should not be null");
        assertTrue(randomPassword.length() > 0, "Generated password should not be empty");
        assertTrue(randomPassword.startsWith("pass_"), "Password should have predictable format");
        
        assertNotEquals(randomUsername, randomPassword, "Generated data should be different");
    }
}
