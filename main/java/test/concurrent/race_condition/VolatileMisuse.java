package test.concurrent.race_condition;

/**
 * 6. Misuse of volatile keyword:
 *
 * The volatile keyword ensures visibility of changes across threads,
 * but it doesn't make compound operations like increment atomic.
 * Use AtomicInteger or synchronization for thread-safe increments.
 */
public class VolatileMisuse {
    private static volatile int counter = 0;

    public static void increment() {
        counter++; // This is still not thread-safe!
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) increment();
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Final value: " + counter);
    }
}