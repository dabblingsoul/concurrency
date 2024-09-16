package test.concurrent.condition;

/**
 * 8. Race condition in lazy initialization of singleton (without double-checked locking):
 *
 * This can result in multiple instances being created. Use double-checked locking with volatile or an initialization-on-demand holder idiom.
 */
public class SingletonWithRaceCondition {
    private static SingletonWithRaceCondition instance;

    private SingletonWithRaceCondition() {}

    public static SingletonWithRaceCondition getInstance() {
        if (instance == null) {
            instance = new SingletonWithRaceCondition(); // Race condition here
        }
        return instance;
    }
}