package test.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Suboptimal use of a Condition object in Java
 * <p>
 * *Inefficient signaling: The code uses condition.signal() instead of condition.signalAll(). This means only one waiting thread is notified at a time, even though multiple threads might be ready to proceed.
 * *Unnecessary waits: All threads enter the wait state, even when it's not their turn to process the shared resource. This leads to more context switches than necessary.
 * *Busy waiting: The main loop in the Worker threads continuously checks the condition, which is inefficient.
 * *No timeout on waits: The await() call doesn't use a timeout, which could lead to threads waiting indefinitely if signals are missed.
 * *Lack of fairness: The lock is not created with the fair parameter set to true, which could lead to some threads being starved of CPU time.
 * <p>
 * These changes address the main inefficiencies in the original code:
 * <p>
 * The use of fair locking prevents thread starvation.
 * 1. Targeted signaling ensures that only the relevant thread is woken up, reducing unnecessary context switches.
 * 2. Each thread waits on its own condition, eliminating spurious wake-ups.
 * 3. The busy waiting problem is solved as threads only wake up when it's their turn to process the shared resource.
 * <p>
 * While this code is much more efficient, there are still potential improvements that could be made depending on the specific use case:
 * 1. Consider using higher-level concurrency utilities like ExecutorService for managing threads.
 * 2. Implement proper shutdown mechanisms for the threads.
 * 3. Use timeouts on the await() calls to prevent indefinite waiting in case of missed signals.
 * 4. Consider using atomic variables or other concurrent data structures if the shared resource allows for it.
 * <p>
 */
public class SuboptimalConditionUsage {
    private static final int THREAD_COUNT = 5;
    private static int sharedResource = 0;
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new Worker(i)).start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Inefficient signaling
        new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    sharedResource++;
                    System.out.println("Resource incremented to: " + sharedResource);
                    // Suboptimal: Signal only one thread at a time
                    condition.signal();
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
                            // Suboptimal: Each thread waits even if it's not its turn
                            condition.await();
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