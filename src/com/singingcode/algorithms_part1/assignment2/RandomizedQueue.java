import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int BUFFER = 8;
    private Item[] store;
    private int end;

    public RandomizedQueue() {
        store = (Item[]) new Object[BUFFER * 2];
        end = -1;
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    public int size() {
        return end + 1;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        end++;
        if (store.length <= end + 1) {
            store = Arrays.copyOf(store, store.length + BUFFER);
        }
        store[end] = item;
    }

    private int getRandomIndex() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int idx = 0;
        if (end > 0) {
            idx = StdRandom.uniform(end);
        }
        return idx;
    }

    public Item dequeue() {
        int idx = getRandomIndex();
        Item tmp = store[idx];
        store[idx] = store[end];
        store[end] = null;
        end--;

        if (store.length >= end + BUFFER * 2) {
            store = Arrays.copyOf(store, end + BUFFER);
        }

        return tmp;
    }

    public Item sample() {
        int idx = getRandomIndex();
        return store[idx];
    }


    private class DequeIterator implements Iterator<Item> {
        private int i;

        public DequeIterator() {
            this.i = 0;
        }

        public boolean hasNext() {
            return i < end;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return store[i++];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return this.new DequeIterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        StdOut.println(rq.isEmpty());
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        StdOut.println("size (6) :" + rq.size());
        rq.enqueue(7);
        rq.enqueue(8);
        rq.enqueue(9);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        StdOut.println("size (12) :" + rq.size());
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        StdOut.println("size (15) :" + rq.size());
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);

        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();
        rq.dequeue();

        StdOut.println("size (10) :" + rq.size());
        StdOut.println("-----");
        StdOut.println("Sample Element:" + rq.sample());
        StdOut.println("Deque2ue Element:" + rq.dequeue());
        StdOut.println("Deque2ue Element:" + rq.dequeue());
        StdOut.println("Deque2ue Element:" + rq.dequeue());
        StdOut.println("size :" + rq.size());
        for (int i : rq) {
            StdOut.println(i);
        }

    }
}
