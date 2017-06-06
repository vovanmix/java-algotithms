package com.singingcode.algorithms_part1.assignment2;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;

// Linked list is an easy solution
public class Deque<Item> implements Iterable<Item> {
    private ArrayList<Item> store;
    private int start;
    private int end;

    private static final int maxBuffer = 8;
    private static final int minBuffer = 4;

    public Deque() {
        store = new ArrayList<>();
        for (int i = 0; i < maxBuffer * 2; i++) {
            store.add(null);
        }
        start = maxBuffer + 1;
        end = maxBuffer;
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    public int size() {
        return end - start + 1;
    }

    private void adjust() {
        if (start > minBuffer || store.size() - end > minBuffer ) {
            return;
        }

        while (start < maxBuffer) {
            for (int i = store.size() - 1; i > 0; i--) {
                store.set(i, store.get(i - 1));
            }
            store.set(0, null);
        }
        while (store.size() - end < maxBuffer) {
            store.add(null);
        }
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        start--;
        store.set(start, item);

        adjust();
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        end++;
        store.set(end, item);

        adjust();
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item item = store.get(start);
        store.set(start, null);
        start++;

        adjust();

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item item = store.get(end);
        store.set(end, null);
        end--;

        adjust();

        return item;
    }


    private class DequeIterator implements Iterator<Item> {
        private int i;
        public DequeIterator() {
            this.i = start;
        }
        public boolean hasNext() {
            return i < end;
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return store.get(i++);
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return this.new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        StdOut.println(deque.isEmpty());
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        StdOut.println(deque.size());
        deque.addLast(5);
        deque.addLast(6);
        deque.addLast(7);
        deque.addLast(8);
        deque.addLast(9);
        deque.removeLast();
        deque.removeFirst();
        for (int i : deque) {
            StdOut.println(i);
        }
    }
}