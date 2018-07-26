import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size = 0;
    private Node head = null;
    private Node tail = null;

    public Deque() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        Node first = new Node(item);
        if (size == 1) {
            head = first;
            tail = first;
        } else {
            first.next = head;
            head.previous = first;
            head = first;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            addFirst(item);
        } else {
            size++;
            Node last = new Node(item);
            tail.next = last;
            last.previous = tail;
            tail = last;
        }
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item firstItem = head.item;
        if (size == 0) {
            head = null;
            tail = null;
        } else {
            Node second = head.next;
            head.next = null;
            head = second;
            head.previous = null;
        }
        return firstItem;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item lastItem = tail.item;
        if (size == 0) {
            head = null;
            tail = null;
        } else {
            Node node = tail.previous;
            tail.previous = null;
            tail = node;
            tail.next = null;
        }
        return lastItem;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node {
        Item item;
        Node next;
        Node previous;

        public Node(Item item) {
            this.item = item;
            this.next = null;
            this.previous = null;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                Item item = current.item;
                current = current.next;
                return item;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
    }
}