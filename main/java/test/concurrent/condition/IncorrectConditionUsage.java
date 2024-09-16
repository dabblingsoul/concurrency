package test.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 7. Incorrect use of Condition variables:
 *
 * Similar to wait/notify, Condition variables should be used with a while loop to guard against spurious wakeups.
 */
public class IncorrectConditionUsage {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean flag = false;

    public void waitForFlag() throws InterruptedException {
        lock.lock();
        try {
            if (!flag) {
                condition.await(); // Should be in a while loop!
            }
        } finally {
            lock.unlock();
        }
    }

    public void setFlag() {
        lock.lock();
        try {
            flag = true;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }
}