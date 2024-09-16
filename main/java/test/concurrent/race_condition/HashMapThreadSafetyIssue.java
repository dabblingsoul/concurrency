package test.concurrent.race_condition;

import java.util.HashMap;
import java.util.Map;

/**
 * 5. Incorrect assumption about HashMap thread-safety:
 * HashMap is not thread-safe,
 * and concurrent access can lead to data corruption or infinite loops.
 * Use ConcurrentHashMap for thread-safe operations.
 */
public class HashMapThreadSafetyIssue {
    private static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) {
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                map.put("Key" + i, "Value" + i);
            }
        });

        Thread reader = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                map.get("Key" + i);
            }
        });

        writer.start();
        reader.start();
    }
}