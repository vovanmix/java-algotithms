package com.singingcode.algorithms_part1.assignment5_kd_trees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Iterator;
import java.util.Stack;

public class KdTree {
    private Node root;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    public KdTree() { }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        int cnt = 0;
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node elt = stack.pop();
            if (elt == null) { continue; }

            cnt++;
            stack.push(elt.rt);
            stack.push(elt.lb);
        }
        return cnt;
    }

    public void insert(Point2D p) {
        if (p == null) { throw new java.lang.IllegalArgumentException(); }
        root = insert(root, p, false);
    }

    /**
     * odd - y
     * even - x
     */
    private Node insert(Node n, Point2D p, boolean even) {
//        if (n == null) {
//            if (parent == null) {
//                RectHV rect = new RectHV(0, 0, 1, 1);
//            } else if (even) {
//                RectHV rect = new RectHV();
//            } else {
//                RectHV rect = new RectHV();
//            }
//            return new Node(p, rect);
//        }

        if (isLeftBottom(n.p, p, even)) {
            if (n.lb == null) {
                n.lb = ...
            } else {
                n.lb = insert(n.lb, p, !even);
            }
        } else {
            if (n.rt == null) {
                n.rt = ...
            } else {
                n.rt = insert(n.rt, p, !even);
            }
        }
        return n;
    }

    public boolean contains(Point2D p) {
        if (p == null) { throw new java.lang.IllegalArgumentException(); }
        return contains(root, p, false);
    }

    private boolean contains(Node n, Point2D p, boolean even) {
        if (n == null) { return false; }
        if (n.p.equals(p)) { return true; }

        if (isLeftBottom(n.p, p, even)) {
            return contains(n.lb, p, !even);
        } else {
            return contains(n.rt, p, !even);
        }
    }

    private boolean isLeftBottom(Point2D current, Point2D point, boolean even) {
        if (even) {
            return current.x() <= point.x();
        } else {
            return current.y() <= point.y();
        }
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
//        use StdDraw.setPenColor(StdDraw.RED) or StdDraw.setPenColor(StdDraw.BLUE) and StdDraw.setPenRadius() before drawing the splitting lines.
        // TODO: draw a line too
        iterator().forEachRemaining(n -> n.p.draw());
    }

    private Iterator<Node> iterator() {
        return new TreeIterator();
    }

    private class TreeIterator implements Iterator<Node> {
        Stack<Node> stack;

        public TreeIterator() {
            stack = new Stack<>();
        }

        public boolean hasNext() {
            return !stack.empty();
        }

        public Node next() {
            if (!hasNext()) { throw new java.util.NoSuchElementException(); }
            Node elt = stack.pop();

            if (elt.rt != null) { stack.push(elt.rt); }
            if (elt.lb != null) { stack.push(elt.lb); }
            return elt;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) { throw new java.lang.IllegalArgumentException(); }

    }

    public Point2D nearest(Point2D p) {
        if (p == null) { throw new java.lang.IllegalArgumentException(); }

    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        RectHV rect = new RectHV(0.1, 0.2, 0.3, 0.5);
        KdTree set = new KdTree();
        set.insert(new Point2D(0.1, 0.1));
        set.insert(new Point2D(0.4, 0.1));
        set.insert(new Point2D(0.1, 0.2));
        set.insert(new Point2D(0.2, 0.4));
        set.insert(new Point2D(0.2, 0.5));

        Point2D nearest = set.nearest(new Point2D(0.11, 0.12));
        assert (nearest.x() == 0.1);
        assert (nearest.y() == 0.1);

        Iterable<Point2D> pointsInRect = set.range(rect);

        pointsInRect.forEach(i -> System.out.println(i.x()));
        pointsInRect.forEach(i -> System.out.println(i.y()));

        Point2D p;
        Iterator<Point2D> iterator = pointsInRect.iterator();
        p = iterator.next();
        assert (p.x() == 0.1): p.x();
        assert (p.y() == 0.2): p.y();
        p = iterator.next();
        assert (p.x() == 0.2): p.x();
        assert (p.y() == 0.4): p.y();
        p = iterator.next();
        assert (p.x() == 0.2);
        assert (p.y() == 0.5);

        set.draw();

        System.out.println("done");
    }
}
