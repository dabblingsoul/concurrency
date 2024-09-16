package test.concurrent.race_condition;

import java.sql.Connection;

/**
 * 2. Incorrect use of ThreadLocal:
 *
 * The issue here is that ThreadLocal resources are not cleaned up,
 * which can lead to memory leaks, especially in application servers.
 * Always remove ThreadLocal values when they're no longer needed:
 *
 * connectionHolder.remove();
 */
public class IncorrectThreadLocalUsage {
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            return createConnection();
        }
    };

    public static Connection getConnection() {
        return connectionHolder.get();
    }

    private static Connection createConnection() {
        // Simulate creating a database connection
        return null; //new Connection(){}; // Just commented out to showcase that it could be a new Connection object
    }

    public static void main(String[] args) {
        // Use connection...
        Connection conn = getConnection();
        // ... but never clean up
    }
}