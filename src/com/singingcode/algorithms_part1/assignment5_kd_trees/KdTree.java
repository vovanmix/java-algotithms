package com.singingcode.algorithms_part1.assignment5_kd_trees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;
import java.util.Stack;

public class KdTree {

    private Node root;

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p) {
            this.p = p;
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

        insert(root, p, false);
    }

    /**
     * odd - y
     * even - x
     */
    private Node insert(Node n, Point2D p, boolean even) {
        if (n == null) { return new Node(p); }

        if (even) {
            if (n.p.x() <= p.x()) {
                n.lb = insert(n.lb, p, false);
            } else if (n.p.x() > p.x()) {
                n.rt = insert(n.rt, p, false);
            }
        } else {
            if (n.p.y() <= p.y()) {
                n.lb = insert(n.lb, p, true);
            } else if (n.p.y() > p.y()) {
                n.rt = insert(n.rt, p, true);
            }
        }
        return n;
    }

    public boolean contains(Point2D p) {
        if (p == null) { throw new java.lang.IllegalArgumentException(); }

    }

    public void draw() {

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

        System.out.println("done");
    }
}
