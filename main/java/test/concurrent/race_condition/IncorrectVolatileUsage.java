package test.concurrent.race_condition;

/**
 * 5. Incorrect use of volatile for compound actions:
 *
 * Volatile ensures visibility but doesn't make compound operations atomic.
 * Use AtomicInteger or synchronize the method for thread-safety.
 */
public class IncorrectVolatileUsage {
    private volatile int count = 0;

    public void increment() {
        count++; // This is not atomic!
    }

    public int getCount() {
        return count;
    }
}