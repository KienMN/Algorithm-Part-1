import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private Node head = null;

    public RandomizedQueue() {
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node(item);
        size++;
        if (size == 1) {
            head = node;
        } else {
            node.next = head;
            head = node;
        }
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item item = null;
        if (size == 0) {
            item = head.item;
            head = null;
        } else {
            int randomNumber = StdRandom.uniform(size + 1);
            if (randomNumber == 0) {
                Node removeNode = head;
                item = head.item;
                head = removeNode.next;
                removeNode.next = null;
            } else {
                int i = 0;
                Node node = head;
                while (i < randomNumber - 1) {
                    node = node.next;
                    i++;
                }
                Node removeNode = node.next;
                item = removeNode.item;
                node.next = removeNode.next;
                removeNode.next = null;
            }
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int randomNumber = StdRandom.uniform(size);
        int i = 0;
        Node node = head;
        while (i < randomNumber) {
            node = node.next;
            i++;
        }
        return node.item;
    }

    private class Node {
        final private Item item;
        private Node next;

        Node(Item item) {
            this.item = item;
            this.next = null;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        final private Item[] items = (Item[]) new Object[size];
        private boolean[] iterated = new boolean[size];
        private int iteratedNode = 0;

        public QueueIterator() {
            Node current = head;
            for (int i = 0; i < size; i++) {
                items[i] = current.item;
                iterated[i] = false;
                current = current.next;
            }
        }

        @Override
        public boolean hasNext() {
            return iteratedNode < size;
        }

        @Override
        public Item next() {
            if (hasNext()) {
                int randomNumber = StdRandom.uniform(size);
                while (iterated[randomNumber]) {
                    randomNumber = StdRandom.uniform(size);
                }
                iterated[randomNumber] = true;
                iteratedNode += 1;
                return items[randomNumber];
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
