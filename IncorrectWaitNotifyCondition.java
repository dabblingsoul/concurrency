package test.concurrent.condition;

/**
 * 3. Incorrect use of wait/notify with condition predicates:
 * This code is susceptible to spurious wakeups and lost notifications. Always use a while loop when waiting:
 *
 * while (!condition) {
 *     wait();
 * }
 */
public class IncorrectWaitNotifyCondition {
    private boolean condition = false;

    public synchronized void waitForCondition() throws InterruptedException {
        if (!condition) {
            wait(); // Should be in a while loop!
        }
        // Process after condition is true
    }

    public synchronized void setCondition() {
        condition = true;
        notify();
    }
}