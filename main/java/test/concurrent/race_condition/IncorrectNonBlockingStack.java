package test.concurrent.race_condition;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 2. Incorrect implementation of a non-blocking algorithm:
 *
 * This implementation suffers from the ABA problem,
 * where the top node might be changed and then changed back between reads, leading
 * to incorrect behavior. Use AtomicStampedReference to solve this issue.
 * @param <T>
 */
public class IncorrectNonBlockingStack<T> {
    private AtomicReference<Node<T>> top = new AtomicReference<>(null);

    private static class Node<T> {
        T item;
        Node<T> next;

        Node(T item) {
            this.item = item;
        }
    }

    public void push(T item) {
        Node<T> newHead = new Node<>(item);
        Node<T> oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public T pop() {
        Node<T> oldHead;
        Node<T> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null) return null;
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }
}