package test.concurrent.race_condition;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 6. Incorrect assumption about thread safety of concurrent collections:
 *
 * Although ConcurrentHashMap is thread-safe, compound operations on it are not atomic.
 * Use compute() or merge() methods for atomic updates.
 */
public class ConcurrentMapMisuse {
    private ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

    public void incrementValue(String key) {
        Integer oldValue = map.get(key);
        if (oldValue == null) {
            map.put(key, 1);
        } else {
            map.put(key, oldValue + 1); // This is not atomic!
        }
    }
}