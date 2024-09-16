package test.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Improvements for Suboptimal use of condition objects
 * -
 *   - **Fair Lock:** We create the ReentrantLock with the fair parameter set to true. This ensures that threads are given access to the lock in the order they requested it, preventing thread starvation.
 *   - **Separate Conditions:** Instead of using a single Condition for all threads, we create a separate Condition for each thread. This allows us to signal only the specific thread that should wake up.
 *   - **Targeted Signaling:** In the main loop, we calculate which thread should be notified and signal only that thread's condition. This eliminates unnecessary wake-ups.
 *   - **Efficient Waiting:** Each Worker thread now waits on its own condition, reducing contention and unnecessary context switches.
 */
public class OptimizedConditionUsage {
    private static final int THREAD_COUNT = 5;
    private static int sharedResource = 0;
    private static final Lock lock = new ReentrantLock(true); // Use fair lock
    private static final Condition[] conditions = new Condition[THREAD_COUNT];

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            conditions[i] = lock.newCondition();
            new Thread(new Worker(i)).start();
        }

        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    sharedResource++;
                    System.out.println("Resource incremented to: " + sharedResource);
                    int threadToNotify = sharedResource % THREAD_COUNT;
                    conditions[threadToNotify].signal(); // Signal only the relevant thread
                } finally {
                    lock.unlock();
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    static class Worker implements Runnable {
        private int id;

        Worker(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (sharedResource % THREAD_COUNT != id) {
                        try {
                            conditions[id].await(); // Each thread waits on its own condition
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Thread " + id + " processed resource: " + sharedResource);
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
