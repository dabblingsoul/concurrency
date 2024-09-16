package test.concurrent.race_condition;

/**
 * 8. Incorrect use of volatile for lazy initialization:
 *
 * Volatile alone doesn't make the lazy initialization thread-safe.
 * Use double-checked locking or an initialization-on-demand holder idiom for
 * thread-safe lazy initialization.
 */
public class IncorrectVolatileLazyInit {
    private volatile ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null) {
            instance = new ExpensiveObject(); // Race condition still possible
        }
        return instance;
    }

    private static class ExpensiveObject {
        public ExpensiveObject() {
            // Expensive initialization
        }
    }
}