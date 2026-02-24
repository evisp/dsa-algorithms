import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList<T> implements Iterable<T> {

    private static class Node<T> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T value) {
        Node<T> n = new Node<>(value);
        n.next = head;
        head = n;

        if (tail == null) {
            tail = head;
        }
        size++;
    }

    public void addLast(T value) {
        Node<T> n = new Node<>(value);

        if (tail == null) {
            head = n;
            tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        size++;
    }

    public T get(int index) {
        checkIndex(index);

        Node<T> curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.value;
    }

    public T removeFirst() {
        if (head == null) {
            throw new NoSuchElementException("Cannot remove from an empty list");
        }

        T removed = head.value;
        head = head.next;
        size--;

        if (head == null) {
            tail = null;
        }
        return removed;
    }

    public boolean remove(T target) {
        if (head == null) return false;

        if (Objects.equals(head.value, target)) {
            removeFirst();
            return true;
        }

        Node<T> prev = head;
        Node<T> curr = head.next;

        while (curr != null) {
            if (Objects.equals(curr.value, target)) {
                prev.next = curr.next;

                if (curr == tail) {
                    tail = prev;
                }

                size--;
                return true;
            }

            prev = curr;
            curr = curr.next;
        }

        return false;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                if (curr == null) throw new NoSuchElementException();
                T value = curr.value;
                curr = curr.next;
                return value;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Node<T> curr = head;
        while (curr != null) {
            sb.append(curr.value);
            if (curr.next != null) sb.append(" -> ");
            curr = curr.next;
        }

        sb.append("]");
        return sb.toString();
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                "Index " + index + " out of bounds for size " + size
            );
        }
    }
}
