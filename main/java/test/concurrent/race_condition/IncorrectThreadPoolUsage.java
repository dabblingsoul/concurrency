package test.concurrent.race_condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 7. Incorrect use of thread pools:
 * This code creates a thread pool but never shuts it down,
 * preventing the program from terminating.
 * Always shut down executor services when they're no longer needed:
 *
 * executor.shutdown();
 */
public class IncorrectThreadPoolUsage {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 100; i++) {
            executor.execute(new Task());
        }

        // The program will never terminate because the executor is not shut down
    }

    static class Task implements Runnable {
        public void run() {
            System.out.println("Task executed by " + Thread.currentThread().getName());
        }
    }
}