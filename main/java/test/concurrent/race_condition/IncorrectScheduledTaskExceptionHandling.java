package test.concurrent.race_condition;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 5. Incorrect handling of exceptions in scheduled tasks:
 *
 * Uncaught exceptions in scheduled tasks silently cancel the task's
 * future executions. Wrap the task logic in a try-catch block to
 * handle exceptions and continue scheduling.
 */
public class IncorrectScheduledTaskExceptionHandling {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        executor.scheduleAtFixedRate(() -> {
            throw new RuntimeException("Task failed");
        }, 0, 1, TimeUnit.SECONDS);
    }
}