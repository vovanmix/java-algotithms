import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Node next;
        Node prev;
        Item value;

        Node(Item item) {
            value = item;
        }
    }

    private Node head, tail;
    private int size;

    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size() <= 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        Node tmp = head;
        head = new Node(item);
        head.next = tmp;
        if (tmp != null) {
            tmp.prev = head;
        }
        if (tail == null) {
            tail = head;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (tail == null) {
            tail = new Node(item);
            head = tail;
        } else {
            Node tmp = tail;
            tail = new Node(item);
            tmp.next = tail;
            tail.prev = tmp;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node tmp = head;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return tmp.value;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node tmp = tail;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }

        size--;
        return tmp.value;
    }


    private class DequeIterator implements Iterator<Item> {
        private Node i;

        public DequeIterator() {
            i = head;
        }

        public boolean hasNext() {
            return i != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Node tmp = i;
            i = i.next;
            return tmp.value;
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
