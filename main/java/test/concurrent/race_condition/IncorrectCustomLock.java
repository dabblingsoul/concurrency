package test.concurrent.race_condition;

/**
 * 3. Incorrect implementation of a custom lock:
 *
 * This implementation doesn't handle spurious wakeups and doesn't ensure that the
 * thread that acquired the lock is the one releasing it.
 * Use java.util.concurrent.locks.ReentrantLock instead of implementing custom
 */
public class IncorrectCustomLock {
    private boolean isLocked = false;

    public synchronized void lock() {
        while (isLocked) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}