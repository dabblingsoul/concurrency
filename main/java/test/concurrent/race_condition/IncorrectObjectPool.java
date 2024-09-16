package test.concurrent.race_condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 10. Incorrect implementation of object pooling:
 *
 * This implementation doesn't handle concurrent access properly and may lead
 * to object leaks. Use BlockingQueue and proper synchronization for thread-safe object pooling.
 *
 * @param <T>
 */
public class IncorrectObjectPool<T> {
    private List<T> pool;

    public IncorrectObjectPool(int size, T prototype) {
        pool = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(prototype);
        }
    }

    public synchronized T acquire() {
        if (pool.isEmpty()) {
            return null;
        }
        return pool.remove(pool.size() - 1);
    }

    public synchronized void release(T obj) {
        pool.add(obj);
    }
}