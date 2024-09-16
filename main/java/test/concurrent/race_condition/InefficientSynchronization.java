package test.concurrent.race_condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 4. Inefficient use of synchronization:
 *
 * This code over-synchronizes by making entire methods synchronized.
 * This can lead to contention and reduced performance.
 * Instead, use finer-grained synchronization or consider using concurrent collections.
 */
public class InefficientSynchronization {
    private List<Integer> numbers = new ArrayList<>();

    public synchronized void addNumber(int number) {
        numbers.add(number);
    }

    public synchronized int getSum() {
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    public synchronized int getSize() {
        return numbers.size();
    }
}