package test.concurrent.race_condition;

/**
 * 9. Incorrect use of thread priorities:
 *
 * Relying heavily on thread priorities can lead to thread starvation
 * and is not portable across platforms. Use proper synchronization and
 * consider using executor services instead.
 */
public class ThreadPriorityMisuse {
    public static void main(String[] args) {
        Thread highPriority = new Thread(() -> {
            while (true) {
                // Compute-intensive task
            }
        });
        highPriority.setPriority(Thread.MAX_PRIORITY);

        Thread lowPriority = new Thread(() -> {
            while (true) {
                // Important but less frequent task
            }
        });
        lowPriority.setPriority(Thread.MIN_PRIORITY);

        highPriority.start();
        lowPriority.start();
    }
}