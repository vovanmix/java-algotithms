package com.singingcode.algorithms_part1.assignment2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

// Other randomized option - array where random element is swapped with
// the last element and the last element is returned
public class RandomizedQueue2<Item> implements Iterable<Item> {
    private Deque2<Item> deque = null;

    public RandomizedQueue2() {
        deque = new Deque2<Item>();
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

    public int size() {
        return deque.size();
    }

    public void enqueue(Item item) {
        int where = StdRandom.uniform(2);
        if (where == 0) {
            deque.addFirst(item);
        } else {
            deque.addLast(item);
        }
    }

    public Item dequeue() {
        return deque.removeFirst();
    }

    public Item sample() {
        Item i = deque.removeFirst();
        deque.addFirst(i);
        return i;
    }

    public Iterator<Item> iterator() {
        return deque.iterator();
    }

    public static void main(String[] args) {
        RandomizedQueue2<Integer> rq = new RandomizedQueue2();
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
        for (int i : rq ) {
            StdOut.println(i);
        }
    }
}