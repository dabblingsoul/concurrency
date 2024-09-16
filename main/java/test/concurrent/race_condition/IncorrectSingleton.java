package test.concurrent.race_condition;

/**
 * The problem with this code is that it's not thread-safe due to potential instruction reordering by the JVM.
 * The instance variable might be returned before the IncorrectSingleton constructor has finished, potentially
 * leading to other threads seeing a partially initialized object.
 *
 * To fix this, you should declare the instance variable as volatile:
 *
 * private static volatile IncorrectSingleton instance;
 */
public class IncorrectSingleton {
    private static IncorrectSingleton instance;

    private IncorrectSingleton() {
    }

    public static IncorrectSingleton getInstance() {
        if (instance == null) {  // First check
            synchronized (IncorrectSingleton.class) {
                if (instance == null) {  // Second check
                    instance = new IncorrectSingleton();
                }
            }
        }
        return instance;
    }
}