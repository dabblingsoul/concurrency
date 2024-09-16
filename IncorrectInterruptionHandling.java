package test.concurrent.condition;

/**
 * The main issues with this code are:
 *
 * The InterruptedException is caught but the interruption status is not restored, effectively swallowing the interruption.
 * The thread continues to run even after being interrupted, which may not be the intended behavior.
 *
 * To fix these issues:
 *
 * Either re-interrupt the thread in the catch block or return from the run method:
 * catch (InterruptedException e) {
 *     Thread.currentThread().interrupt(); // Restore the interruption status
 *     return; // Exit the thread's run method
 * }
 */
public class IncorrectInterruptionHandling {
    public static void main(String[] args) {
        Thread worker = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // Simulate some work
                    Thread.sleep(1000);
                    System.out.println("Working...");
                }
            } catch (InterruptedException e) {
                // Swallowing the interruption
                System.out.println("Interrupted, but continuing...");
            }
            System.out.println("Thread finished.");
        });

        worker.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        worker.interrupt();
    }
}