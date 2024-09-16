package test.concurrent.race_condition;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 4. Incorrect use of weak references in caches:
 * @param <K>
 * @param <V>
 *
 * This implementation may lead to memory leaks as the map entries
 * themselves are not weak references. Use WeakHashMap instead for a
 * proper weak reference-based cache.
 */
public class IncorrectWeakReferenceCache<K, V> {
    private final Map<K, WeakReference<V>> cache = new HashMap<>();

    public V get(K key) {
        WeakReference<V> ref = cache.get(key);
        return (ref != null) ? ref.get() : null;
    }

    public void put(K key, V value) {
        cache.put(key, new WeakReference<>(value));
    }
}