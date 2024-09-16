package test.concurrent.race_condition;

/**
 * 10. Incorrect assumption about the atomicity of ++ operator:
 * The ++ operator is not atomic, which can lead to lost updates.
 * Use AtomicInteger or synchronize the increment operation.
 *
 */
public class NonAtomicIncrement {
    private static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter++; // This is not an atomic operation!
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter++;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final count: " + counter); // May not be 20000
    }
}