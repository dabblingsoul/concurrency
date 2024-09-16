package test.concurrent.race_condition;

/**
 * 2. Memory consistency error with non-volatile long and double:
 *
 * 64-bit primitives (long and double) are not guaranteed to have atomic reads/writes.
 * This can lead to inconsistent values. Use volatile or AtomicLong to ensure atomicity.
 */
public class NonVolatileLongIssue {
    private long sharedLong;

    public void setLong(long value) {
        sharedLong = value;
    }

    public long getLong() {
        return sharedLong;
    }

    public static void main(String[] args) {
        NonVolatileLongIssue example = new NonVolatileLongIssue();

        new Thread(() -> {
            while (true) {
                example.setLong(Long.MAX_VALUE);
            }
        }).start();

        new Thread(() -> {
            while (true) {
                long value = example.getLong();
                if (value != 0 && value != Long.MAX_VALUE) {
                    System.out.println("Inconsistent state detected: " + value);
                }
            }
        }).start();
    }
}
