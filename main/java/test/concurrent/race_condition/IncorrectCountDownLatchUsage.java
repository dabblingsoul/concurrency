package test.concurrent.race_condition;

import java.util.concurrent.CountDownLatch;

/**
 * 1. Incorrect use of CountDownLatch:
 * The issue here is that if the worker thread is interrupted,
 * the main thread might wait forever. To fix this, use await() with a timeout:
 *
 * if (!latch.await(2, TimeUnit.SECONDS)) {
 *     System.out.println("Timed out waiting for worker");
 * }
 */
public class IncorrectCountDownLatchUsage {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Thread worker = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Worker finished");
                latch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        worker.start();
        latch.await(); // This might block forever if the worker thread is interrupted
        System.out.println("Main thread continues");
    }
}