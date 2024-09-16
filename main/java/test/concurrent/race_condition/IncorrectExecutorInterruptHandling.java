package test.concurrent.race_condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 8. Incorrect handling of InterruptedException in executors:
 *
 * Swallowing InterruptedException prevents the executor from shutting down properly.
 * Propagate the exception or set the thread's interrupt status.
 */
public class IncorrectExecutorInterruptHandling {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                // Swallowing the exception
            }
        });

        executor.shutdownNow();
    }
}
