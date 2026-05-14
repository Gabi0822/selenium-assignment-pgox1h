package utils;

/**
 * Utility class for generating random test data.
 */
public class DataGenerator {

    /**
     * Generate random username with timestamp and random number.
     */
    public static String generateRandomUsername() {
        return "user_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
    }

    /**
     * Generate random password with timestamp.
     */
    public static String generateRandomPassword() {
        return "pass_" + System.currentTimeMillis();
    }
}
