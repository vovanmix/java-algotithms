package com.singingcode.algorithms_part1.assignment2;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        RandomizedQueue2<String> rqueue = new RandomizedQueue2<String>();
        while (!StdIn.isEmpty()) {
            rqueue.enqueue(StdIn.readString());
        }
        for (int i = 0; i < count; i++) {
            System.out.println(rqueue.dequeue());
        }
    }
}