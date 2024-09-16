package test.concurrent.race_condition;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 9. Incorrect use of ThreadLocalRandom:
 *
 * ThreadLocalRandom.current() should be called within the method,
 * not stored as a static field. Each thread needs its own instance.
 */
public class IncorrectThreadLocalRandomUsage {
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    public static int getRandomNumber() {
        return random.nextInt(100);
    }
}