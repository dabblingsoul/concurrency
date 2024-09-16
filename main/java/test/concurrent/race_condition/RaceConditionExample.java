package test.concurrent.race_condition;

/**
 * 1. Race Condition in Lazy Initialization:
 *
 * This code has a race condition where multiple threads could
 * create multiple instances of ExpensiveObject. To fix this,
 * use proper synchronization or the initialization-on-demand holder idiom.
 *
 */
public class RaceConditionExample {
    private static ExpensiveObject instance = null;

    public static ExpensiveObject getInstance() {
        if (instance == null) {
            instance = new ExpensiveObject(); // Race condition here
        }
        return instance;
    }
}

class ExpensiveObject {
    public ExpensiveObject() {
        // Simulate expensive initialization
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}