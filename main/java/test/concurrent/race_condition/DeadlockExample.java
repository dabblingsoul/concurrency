package test.concurrent.race_condition;

/**
 * 3. Deadlock due to inconsistent lock ordering:
 *
 * This code can result in a deadlock because
 * the two threads acquire locks in different orders.
 * To fix this, ensure consistent lock ordering across all threads.
 *
 */
public class DeadlockExample {
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (LOCK1) {
                System.out.println("Thread 1: Holding lock 1...");
                try { Thread.sleep(10); } catch (InterruptedException e) {}
                System.out.println("Thread 1: Waiting for lock 2...");
                synchronized (LOCK2) {
                    System.out.println("Thread 1: Holding lock 1 & 2...");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (LOCK2) {
                System.out.println("Thread 2: Holding lock 2...");
                try { Thread.sleep(10); } catch (InterruptedException e) {}
                System.out.println("Thread 2: Waiting for lock 1...");
                synchronized (LOCK1) {
                    System.out.println("Thread 2: Holding lock 2 & 1...");
                }
            }
        }).start();
    }
}