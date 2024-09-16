package test.concurrent.race_condition;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 7. Incorrect implementation of a spin lock
 *
 * This implementation may lead to high CPU usage and poor performance
 * under contention. Use Thread.yield() or LockSupport.parkNanos()
 * to reduce CPU usage during spinning.
 */
public class IncorrectSpinLock {
    private AtomicBoolean locked = new AtomicBoolean(false);

    public void lock() {
        while (!locked.compareAndSet(false, true)) {
            // Spin
        }
    }

    public void unlock() {
        locked.set(false);
    }
}