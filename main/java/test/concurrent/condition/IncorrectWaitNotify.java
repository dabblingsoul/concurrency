package test.concurrent.condition;

/**
 * This code has several issues:
 *
 * The waiter thread doesn't check the condition before entering the wait state, which can lead to missed signals.
 * The notify() call might happen before the wait() call, causing the waiter to wait indefinitely.
 * There's no timeout on the wait() call, which could lead to the thread waiting forever if a signal is missed.
 *
 * To fix these issues:
 *
 * Always check the condition in a while loop before and after waiting.
 * Consider using notifyAll() instead of notify() to wake up all waiting threads.
 * Use wait() with a timeout to avoid indefinite waiting.
 *
 */
public class IncorrectWaitNotify {
    private static final Object lock = new Object();
    private static boolean condition = false;

    public static void main(String[] args) {
        Thread waiter = new Thread(() -> {
            synchronized (lock) {
                while (!condition) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Waiter thread processed.");
            }
        });

        Thread notifier = new Thread(() -> {
            synchronized (lock) {
                condition = true;
                lock.notify();
            }
        });

        waiter.start();
        notifier.start();
    }
}