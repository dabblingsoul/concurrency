package test.concurrent.condition;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CountDownLatch;

/**
 * Use fair locks when thread starvation is a significant concern and you need to ensure that all threads get a chance to acquire the lock in a timely manner.
 * Consider fair locks in situations where predictable ordering of lock acquisition is more important than raw performance.
 * Use fair locks in scenarios where the critical section (the code protected by the lock) is relatively long-running, as the overhead of fairness becomes less significant relative to the work being done.
 * Consider fair locks in systems where you need to maintain a strict first-come-first-served order for lock acquisition.
 *
 * <p>
 * Setting fairness to true in a ReentrantLock can indeed have some downsides. Let's explore the implications of always using fair locks:
 * 1. Performance Overhead: Fair locks typically have lower overall throughput and reduced performance compared to non-fair locks. This is because maintaining fairness requires more bookkeeping and coordination.
 * 2. Increased Latency: In scenarios with high contention, threads may experience increased latency when acquiring the lock, as they must wait for all previously queued threads to acquire and release the lock before getting their turn.
 * 3. Higher CPU Usage: Fair locks can lead to more context switches and higher CPU usage, especially in systems with many threads competing for the same lock.
 * 4. Potential for Priority Inversion: In some cases, fair locking can lead to a form of priority inversion where high-priority threads are forced to wait for lower-priority threads that arrived earlier.
 * 5. Reduced Concurrency in Some Scenarios: In certain situations, fair locking can actually reduce concurrency by forcing a strict ordering of lock acquisitions when a more opportunistic approach might allow for better parallelism.
 */
public class LockFairnessBenchmark {
    private static final int THREAD_COUNT = 10;
    private static final int ITERATIONS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        benchmarkLock(new ReentrantLock(false), "Unfair Lock");
        benchmarkLock(new ReentrantLock(true), "Fair Lock");
    }

    private static void benchmarkLock(Lock lock, String lockType) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    startSignal.await();
                    for (int j = 0; j < ITERATIONS; j++) {
                        lock.lock();
                        try {
                            // Simulate some work
                            Thread.yield();
                        } finally {
                            lock.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneSignal.countDown();
                }
            }).start();
        }

        long start = System.nanoTime();
        startSignal.countDown();
        doneSignal.await();
        long end = System.nanoTime();

        System.out.printf("%s took %.2f ms%n", lockType, (end - start) / 1_000_000.0);
    }
}
