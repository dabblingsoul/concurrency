package test.concurrent.condition;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 10. Incorrect use of condition queues:
 * @param <E>
 *
 * This implementation may lead to unnecessary context switches due to notifyAll().
 * Use separate condition variables for "not full" and "not empty" states
 * for more efficient signaling.
 */
public class IncorrectConditionQueue<E> {
    private final Queue<E> queue = new LinkedList<>();
    private final int maxSize;

    public IncorrectConditionQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void put(E element) throws InterruptedException {
        while (queue.size() == maxSize) {
            wait();
        }
        queue.add(element);
        notifyAll();
    }

    public synchronized E take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        E item = queue.remove();
        notifyAll();
        return item;
    }
}