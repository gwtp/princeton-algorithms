import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int count;

    private class ListIterator implements Iterator<Item> {

        private int i;
        private int size;
        private int[] randomNumbers;

        public ListIterator() {
            size = size();
            randomNumbers = StdRandom.permutation(size());
        }

        public boolean hasNext() {
            return i < size;
        }

        public void remove() {
            throw new UnsupportedOperationException("Iterator.remove() not supported");
        }

        public Item next() {
            if (i >= size) {
                throw new java.util.NoSuchElementException("queue is empty");
            }

            int randomElement = randomNumbers[i++];
            return items[randomNumbers[randomElement]];
        }

    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        if (size() == items.length) {
            resize(2 * items.length);
        }
        items[count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        int lastItem;
        switch (size()) {
            case 0:
                throw new java.util.NoSuchElementException("queue is empty");
            case 1:
                // There is only 1 item remaining in the queue, just return it.
                lastItem = 0;
                break;
            default:
                // Find a random number between 0 and size of array.
                int randomItem = StdRandom.uniformInt(size());
                lastItem = size() - 1;

                // swap items[i] with items[i-1].
                swap(items, randomItem, lastItem);
        }

        Item item = items[lastItem];
        items[lastItem] = null;
        count--;

        if (size() > 0 && size() == items.length / 4) {
            resize(items.length / 2);
        }

        return item;
    }

    private void swap(Item[] a, int i, int j) {
        if (i != j) {
            Item tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            copy[i] = items[i];
        }

        items = copy;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size() == 0) {
            throw new java.util.NoSuchElementException("queue is empty");
        }

        return items[StdRandom.uniformInt(size())];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();

    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();
        StdOut.printf("isEmpty(): %b\n", q.isEmpty());
        StdOut.printf("size(): %d\n", q.size());
        String[] items = { "Bar", "Foo", "Baz" };
        for (String s : items) {
            StdOut.printf("enqueue(): %s\n", s);
            q.enqueue(s);
        }
        StdOut.printf("isEmpty(): %b\n", q.isEmpty());
        StdOut.printf("size(): %d\n", q.size());

        while (!q.isEmpty()) {
            StdOut.printf("size(): %d\n", q.size());
            StdOut.printf("q.sample(): %s\n", q.sample());
            StdOut.printf("q.dequeue(): %s\n", q.dequeue());
            StdOut.printf("isEmpty(): %b\n", q.isEmpty());
            StdOut.printf("size(): %d\n", q.size());
        }

        for (String s : items) {
            StdOut.printf("enqueue(): %s\n", s);
            q.enqueue(s);
        }

        for (String s : q) {
            StdOut.printf("%s\n", s);
        }
        StdOut.printf("size(): %d\n", q.size());
        StdOut.printf("isEmpty(): %b\n", q.isEmpty());
    }

}