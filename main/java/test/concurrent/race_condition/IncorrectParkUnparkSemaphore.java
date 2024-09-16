package test.concurrent.race_condition;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 6. Incorrect use of park/unpark for implementing a semaphore:
 *
 * This implementation may lead to lost unparks and potential deadlocks.
 * Use a queue to track waiting threads and ensure fairness.
 */
public class IncorrectParkUnparkSemaphore {
    private final AtomicInteger permits;

    public IncorrectParkUnparkSemaphore(int permits) {
        this.permits = new AtomicInteger(permits);
    }

    public void acquire() {
        while (true) {
            if (permits.decrementAndGet() >= 0) {
                return;
            }
            permits.incrementAndGet();
            LockSupport.park();
        }
    }

    public void release() {
        permits.incrementAndGet();
        LockSupport.unpark(Thread.currentThread());
    }
}