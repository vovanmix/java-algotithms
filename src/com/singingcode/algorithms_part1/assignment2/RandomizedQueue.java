package com.singingcode.algorithms_part1.assignment2;

import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private ArrayList<Item> store;
    private int end;
    private static final int buffer = 8;

    public RandomizedQueue() {
        store = new ArrayList<>();
        for (int i = 0; i < buffer * 2; i++) {
            store.add(null);
        }
        end = -1;
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    public int size() {
        return end + 1;
    }

    public void enqueue(Item item) {
        end++;
        store.set(end, item);
    }

    public Item dequeue() {
        int idx = StdRandom.uniform(end);
        Item tmp = store.get(idx);
        store.set(idx, store.get(end));
        store.set(end, null);
        end--;
        return tmp;
    }

    public Item sample() {
        int idx = StdRandom.uniform(end);
        return store.get(idx);
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
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        StdOut.println(rq.isEmpty());
        //rq.dequeue();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        rq.enqueue(6);
        StdOut.println("size :" + rq.size());
        rq.enqueue(7);
        rq.enqueue(8);
        rq.enqueue(9);
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
