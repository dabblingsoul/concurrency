package test.concurrent.race_condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**1. Incorrect use of ThreadLocal with thread pools:
 * The issue here is that ThreadLocal values persist between tasks in a thread pool,
 * potentially causing unexpected behavior.
 * Always remove ThreadLocal values at the end of each task when using thread pools.
 */
public class ThreadLocalWithPoolIssue {
    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                int value = threadLocal.get();
                threadLocal.set(value + 1);
                System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
            });
        }

        executor.shutdown();
    }
}