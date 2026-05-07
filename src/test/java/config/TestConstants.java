package config;

/**
 * Constants for test configuration including URLs, credentials, and timeouts.
 */
public class TestConstants {
    public static final String BASE_URL = "https://demo.bludit.com/admin/";
    public static final String HOME_URL = "https://demo.bludit.com/";
    public static final String DASHBOARD_URL = "https://demo.bludit.com/admin/dashboard/";
    public static final String LOGOUT_URL = "https://demo.bludit.com/admin/logout";

    public static final String LOGIN_USERNAME = "admin";
    public static final String LOGIN_PASSWORD = "demo123";

    public static final int EXPLICIT_WAIT_TIMEOUT_SECONDS = 10;
    public static final int SHORT_WAIT_TIMEOUT_SECONDS = 5;

    private TestConstants() {
        // Utility class, no instantiation
    }
}
