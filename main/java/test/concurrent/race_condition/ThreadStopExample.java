package test.concurrent.race_condition;

/**
 * 6.
 * Thread.stop() is deprecated
 * because it can leave shared data in an inconsistent state.
 * Use a volatile boolean flag or interruption for graceful shutdown.
 */
public class ThreadStopExample {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
        });

        worker.start();
        Thread.sleep(1000);
        worker.stop(); // Deprecated and dangerous!
    }
}