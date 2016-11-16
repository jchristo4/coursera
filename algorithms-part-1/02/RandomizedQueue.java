import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

/**
 * Generic data type RandomizedQueue (generalization of a stack and a queue that
 * supports adding items to the back, and removal/retrieval of random items
 * from the data structure)
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first;     // pointer to first item in the randomizedQueue
    private Node last;      // pointer to last item in the randomizedQueue
    private int size;       // number of items in the randomizedQueue

    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    /**
     * An iterator for RandomizedQueue data structure
     */
    private class RandomizedQueueIterator implements Iterator<Item> {
        private Node ptr;

        public RandomizedQueueIterator(Node first) {
            ptr = first;
        }

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

    public RandomizedQueue() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Is this randomizedQueue empty?
     * @return {@code true} if this randomizedQueue is empty; {@code false}
     * otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the randomizedQueue.
     * @return the number of items in the randomizedQueue
     */
    public int size() {
        return size;
    }

    /**
     * Adds an item to the end of this randomizedQueue
     * @param item the item to add
     * @throws NullPointerException if the item to add is null
     */
    public void enqueue(Item item) {
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
     * Removes and returns a random item from this randomizedQueue.
     * @return random item from the randomizedQueue
     * @throws NoSuchElementException if this randomizedQueue is empty
     */
    public Item dequeue() {
        Node ptr, prev;
        Item item;
        int rand;

        if (isEmpty()) {
            throw new
                java.util.NoSuchElementException("RandomizedQueue underflow");
        }

        rand = StdRandom.uniform(size());

        ptr = first;
        prev = first;

        for (int i = 0; i < rand; i++) {
            prev = ptr;
            ptr = ptr.next;
        }

        if (rand == 0) {
            first = ptr.next;
        }

        if (ptr.next == null) {
            prev.next = null;
            last = prev;
        } else {
            prev.next = ptr.next;
        }

        --size;

        if (isEmpty()) {
            last = first;
        }

        item = ptr.item;
        ptr = null;

        return item;
    }

   /**
     * Returns a random item from this randomizedQueue.
     * @return random item from the randomizedQueue
     * @throws NoSuchElementException if this randomizedQueue is empty
     */
    public Item sample() {
        Node ptr;
        int rand;

        if (isEmpty()) {
            throw new
                java.util.NoSuchElementException("RandomizedQueue underflow");
        }

        rand = StdRandom.uniform(size());
        ptr = first;

        for (int i = 0; i < rand; i++) {
            ptr = ptr.next;
        }

        return ptr.item;
    }

    /**
     * Returns an iterator to this randomizedQueue that iterates through the items.
     * @return an iterator to this randomizedQueue that iterates through the items
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(first);
    }

    /**
     * Unit tests the {@code Deque} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();

        for (int i = 1; i <= 5; i++) {
            rq.enqueue(i);
        }

        System.out.println("sample: " + rq.sample());
        System.out.println("sample: " + rq.sample());

        for (int i : rq) {
            System.out.println(i);
        }

        System.out.println("removed: " + rq.dequeue());
        System.out.println("removed: " + rq.dequeue());

        for (int i : rq) {
            System.out.println(i);
        }

    }

}