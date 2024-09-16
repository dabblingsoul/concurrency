package test.concurrent.race_condition;

/**
 * 5. Incorrect assumption about atomicity:
 *
 * The count++ operation is not atomic, which can lead to lost updates.
 * Use AtomicLong or synchronize the increment method to fix this issue.
 */
public class IncorrectAtomicityAssumption {
    private long count = 0;

    public void increment() {
        count++; // This operation is not atomic!
    }

    public long getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        IncorrectAtomicityAssumption counter = new IncorrectAtomicityAssumption();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) counter.increment();
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) counter.increment();
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Final count: " + counter.getCount());
    }
}