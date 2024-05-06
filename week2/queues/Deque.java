import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int count;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Iterator.remove() not supported");
        }

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException("no more items to return");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst != null) {
            oldfirst.prev = first;
        }
        // If this is the first item added to the front, also make it the back.
        if (size() == 0) {
            last = first;
        }
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.prev = oldlast;
        if (oldlast != null) {
            oldlast.next = last;
        }
        // If this is the first item added to the back, also make it the front.
        if (size() == 0) {
            first = last;
        }
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("cannot remove item, deque is empty");
        }
        Node oldfirst = first;
        first = oldfirst.next;
        if (first != null) {
            first.prev = null;
        }
        // If this is the last item to remove from the front, make the back null.
        if (size() == 1) {
            first = null;
            last = null;
        }
        count--;
        return oldfirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("cannot remove item, deque is empty");
        }
        Node oldlast = last;
        if (oldlast.prev != null) {
            oldlast.prev.next = null;
            last = oldlast.prev;
        }
        // If this is the last item to remove from the back, make the front null.
        if (size() == 1) {
            first = null;
            last = null;
        }
        count--;
        return oldlast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> d = new Deque<>();
        StdOut.printf("isEmpty(): %b\n", d.isEmpty());
        StdOut.printf("size(): %d\n", d.size());
        String[] items = { "Bar", "Foo" };
        for (String s : items) {
            d.addFirst(s);
        }
        d.addLast("Baz");
        StdOut.printf("isEmpty(): %b\n", d.isEmpty());
        StdOut.printf("size(): %d\n", d.size());

        for (String s : d)
            StdOut.printf("%s\n", s);

        StdOut.printf("removeFirst(): %s\n", d.removeFirst());
        StdOut.printf("removeLast(): %s\n", d.removeLast());

        for (String s : d)
            StdOut.printf("%s\n", s);

        StdOut.printf("isEmpty(): %b\n", d.isEmpty());
        StdOut.printf("size(): %d\n", d.size());

        StdOut.printf("removeLast(): %s\n", d.removeLast());

        StdOut.printf("isEmpty(): %b\n", d.isEmpty());
        StdOut.printf("size(): %d\n", d.size());

        StdOut.println("addFirst(\"Bar\")");
        d.addFirst("Bar");
        StdOut.printf("removeFirst(): %s\n", d.removeFirst());

        StdOut.println("addLast(\"Bar\")");
        d.addLast("Bar");
        d.addFirst("Foo");
        d.addLast("Baz");
        StdOut.printf("removeLast(): %s\n", d.removeLast());
        StdOut.printf("removeLast(): %s\n", d.removeLast());
        StdOut.printf("removeLast(): %s\n", d.removeFirst());
    }

}