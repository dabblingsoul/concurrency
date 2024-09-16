package test.concurrent.race_condition;

/**
 * 7. Incorrect handling of InterruptedException:
 * Swallowing InterruptedException causes the interruption to be lost.
 * Either rethrow it, set the interrupt flag, or handle it appropriately.
 */
public class IncorrectInterruptedExceptionHandling implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            // Silently swallow the exception
        }
        // Continue as if nothing happened
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new IncorrectInterruptedExceptionHandling());
        t.start();
        Thread.sleep(3000);
        t.interrupt();
    }
}