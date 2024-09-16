package test.concurrent.map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentHashMapFixed {
    private static final int NUM_THREADS = 100;
    private static final int OPERATIONS_PER_THREAD = 10000;
    private static final String KEY = "testKey";

    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();
        map.put(KEY, new AtomicInteger(0));  // Initialize with AtomicInteger

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
                            // Use compute to atomically update the value
                            map.compute(KEY, (k, v) -> {
                                if (v == null) {
                                    v = new AtomicInteger();
                                }
                                v.incrementAndGet();
                                return v;
                            });
                            putCount.incrementAndGet();
                        } else {
                            AtomicInteger value = map.get(KEY);
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
        System.out.println("Final value: " + map.get(KEY).get());
    }
}