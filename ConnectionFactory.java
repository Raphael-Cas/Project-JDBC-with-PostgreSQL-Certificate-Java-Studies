package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Single place in the application that knows how to open a database connection.
 * Every repository calls ConnectionFactory.getConnection() instead of repeating
 * the URL, user and password.
 *
 * Settings can be overridden with environment variables (DB_URL, DB_USER, DB_PASSWORD),
 * which keeps real credentials out of the source code and out of Git.
 */
public final class ConnectionFactory {

    // Default values, used when the environment variable is not set.
    // Never commit your real password to a public repository.
    private static final String URL = env("DB_URL", "jdbc:postgresql://localhost:5432/biblioteca");
    private static final String USER = env("DB_USER", "postgres");
    private static final String PASSWORD = env("DB_PASSWORD", "your_password");

    // Utility class: it should not be instantiated.
    private ConnectionFactory() {
    }

    /**
     * Opens a NEW connection every time it is called.
     * The caller is responsible for closing it (use try-with-resources).
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Returns the value of an environment variable, or a default when it is missing/blank.
    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
