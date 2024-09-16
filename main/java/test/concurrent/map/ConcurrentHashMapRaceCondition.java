package test.concurrent.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Scenario: In a multithreaded environment, it's possible for a ConcurrentHashMap to seemingly "lose" a value that was put into it. This situation can occur due to the following reasons:
 *
 * Visibility issues: If one thread puts a value into the map and another thread immediately tries to retrieve it, there's a small chance that the second thread might not see the update immediately due to memory visibility issues.
 * Race conditions: If multiple threads are putting and removing values concurrently, it's possible that a value is removed right after it was put, making it appear as if the value was never there.
 * Hash collisions: In rare cases, if two keys have the same hash code, one might overwrite the other, especially if the keys are not properly implemented (e.g., violating the contract of equals() and hashCode() methods).
 */
public class ConcurrentHashMapRaceCondition {
    private static final int NUM_THREADS = 100;
    private static final int OPERATIONS_PER_THREAD = 10000;
    private static final String KEY = "testKey";

    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(NUM_THREADS);
        AtomicInteger putCount = new AtomicInteger(0);
        AtomicInteger getCount = new AtomicInteger(0);
        AtomicInteger nullCount = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            executor.submit(() -> {
                try {
                    startLatch.await();
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        if (Math.random() < 0.5) {
                            map.put(KEY, j);
                            putCount.incrementAndGet();
                        } else {
                            Integer value = map.get(KEY);
                            getCount.incrementAndGet();
                            if (value == null) {
                                nullCount.incrementAndGet();
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    endLatch.countDown();
                }
            });
        }

        long startTime = System.currentTimeMillis();
        startLatch.countDown();
        endLatch.await();
        long endTime = System.currentTimeMillis();

        executor.shutdown();

        System.out.println("Execution time: " + (endTime - startTime) + " ms");
        System.out.println("Total operations: " + (putCount.get() + getCount.get()));
        System.out.println("Put operations: " + putCount.get());
        System.out.println("Get operations: " + getCount.get());
        System.out.println("Null gets: " + nullCount.get());
        System.out.println("Final map size: " + map.size());
    }
}