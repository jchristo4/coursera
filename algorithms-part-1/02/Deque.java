
import java.util.Iterator;

/**
 * Generic data type Deque (generalization of a stack and a queue that supports
 * adding and removing items from front / back of the data structure)
 */

public class Deque<Item> implements Iterable<Item> {

    private Node first;     // pointer to first item in the deque
    private Node last;      // pointer to last item in the deque
    private int size;       // number of items in the deque


    private class Node {
        Item item;
        Node next;
        Node prev;
    }


    /**
     * An iterator for Deque data structure
     */
    private class DequeIterator implements Iterator<Item> {
        private Node ptr = first;

        public boolean hasNext() {
            return ptr != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item item;

            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            item = ptr.item;
            ptr = ptr.next;
            return item;
        }
    }


    public Deque() {
        first = null;
        last = null;
        size = 0;
    }


    /**
     * Is this deque empty?
     * @return {@code true} if this deque is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Returns the number of items in the deque.
     * @return the number of items in the degue
     */
    public int size() {
        return size;
    }


    /**
     * Adds an item to this deque from front of the data structure.
     * @param item the item to add
     * @throws NullPointerException if the item to add is null
     */
    public void addFirst(Item item) {
        Node oldFirst;

        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        oldFirst = first;
        first = new Node();

        first.item = item;
        first.next = oldFirst;
        first.prev = null;

        if (isEmpty()) {
            last = first;
        } else {
            oldFirst.prev = first;
        }

        ++size;
    }


    /**
     * Adds an item to this deque from back of the data structure.
     * @param item the item to add
     * @throws NullPointerException if the item to add is null
     */
    public void addLast(Item item) {
        Node oldLast;

        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        oldLast = last;
        last = new Node();

        last.item = item;
        last.next = null;
        last.prev = oldLast;

        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }

        ++size;
    }


    /**
     * Removes and returns an item from front of this deque.
     * @return item that is front in the deque
     * @throws NoSuchElementException if this deque is empty
     */
    public Item removeFirst() {
        Item item;

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Deque underflow");
        }

        item = first.item;
        first = first.next;
        --size;

        if (isEmpty()) {
            last = first;
        } else {
            first.prev = null;
        }

        return item;
    }


    /**
     * Removes and returns an item from back of this deque.
     * @return item that is back in the deque
     * @throws NoSuchElementException if this deque is empty
     */
    public Item removeLast() {
        Item item;

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("Deque underflow");
        }

        item = last.item;
        last = last.prev;
        --size;

        if (isEmpty()) {
            first = last;
        } else {
            last.next = null;
        }

        return item;
    }


    /**
     * Returns an iterator to this deque that iterates through the items.
     * @return an iterator to this deque that iterates through the items
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }


    /**
     * Unit tests the {@code Deque} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

    }

}