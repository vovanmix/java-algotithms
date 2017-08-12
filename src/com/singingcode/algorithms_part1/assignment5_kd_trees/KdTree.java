package com.singingcode.algorithms_part1.assignment5_kd_trees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (root == null) {
            root = new Node(p, new RectHV(0f, 0f, 1f, 1f));
            return;
        }
        insert(root, p, 0);
    }

    private void insert(Node node, Point2D p, int depth) {
        if (node == null)
            return;

        if (node.p.equals(p))
            return;

        boolean xCoordinate = depth % 2 == 0;
        if (xCoordinate) {
            if (p.x() < node.p.x()) {
                if (node.lb == null) {
                    node.lb = new Node(p, new RectHV(node.rect.xmin(),
                            node.rect.ymin(), node.p.x(), node.rect.ymax()));
                } else {
                    insert(node.lb, p, depth + 1);
                }
            } else {
                if (node.rt == null) {
                    node.rt = new Node(p, new RectHV(node.p.x(),
                            node.rect.ymin(), node.rect.xmax(),
                            node.rect.ymax()));
                } else {
                    insert(node.rt, p, depth + 1);
                }
            }
        } else {
            if (p.y() < node.p.y()) {
                if (node.lb == null) {
                    node.lb = new Node(p, new RectHV(node.rect.xmin(),
                            node.rect.ymin(), node.rect.xmax(), node.p.y()));
                } else {
                    insert(node.lb, p, depth + 1);
                }
            } else {
                if (node.rt == null) {
                    node.rt = new Node(p, new RectHV(node.rect.xmin(),
                            node.p.y(), node.rect.xmax(), node.rect.ymax()));
                } else {
                    insert(node.rt, p, depth + 1);
                }
            }
        }
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
        draw(root, 0);
    }

    private void draw(Node node, int depth) {
        if (node == null)
            return;

        StdDraw.setPenRadius(.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        node.p.draw();
        StdDraw.setPenRadius();
        if (depth % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(),
                    node.rect.ymax());
            draw(node.lb, depth + 1);
            draw(node.rt, depth + 1);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(),
                    node.p.y());
            draw(node.lb, depth + 1);
            draw(node.rt, depth + 1);
        }
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
        List<Point2D> list = new ArrayList<Point2D>();
        range(rect, root, list);
        return list;
    }

    private void range(RectHV rect, Node node, List<Point2D> list) {
        if (node == null)
            return;

        if (rect.contains(node.p))
            list.add(node.p);

        if (rect.intersects(node.rect)) {
            range(rect, node.lb, list);
            range(rect, node.rt, list);
        }
    }

    private Point2D nearest = null;
    private double distanceToNearest = Double.POSITIVE_INFINITY;

    public Point2D nearest(Point2D p) {
        if (p == null)
            return null;
        nearest = root.p;
        distanceToNearest = root.p.distanceSquaredTo(p);
        nearest(p, root, 0);
        return nearest;
    }

    private void nearest(Point2D p, Node node, int depth) {
        if (node == null)
            return;

        // if the closest point discovered so far is closer than the distance
        // between the query point and the rectangle corresponding to a node,
        // there is no need to explore that node (or its subtrees).
        if (node.rect.distanceSquaredTo(p) < distanceToNearest) {
            double distance = node.p.distanceSquaredTo(p);
            if (distance < distanceToNearest) {
                nearest = node.p;
                distanceToNearest = distance;
            }

            // The effectiveness of the pruning rule depends on quickly finding
            // a nearby point. To do this, organize your recursive method so
            // that when there are two possible subtrees to go down, you always
            // choose the subtree that is on the same side of the splitting line
            // as the query point as the first subtree to explore the closest
            // point found while exploring the first subtree may enable pruning
            // of the second subtree.
            boolean xCoordinate = depth % 2 == 0;
            if ((xCoordinate && p.x() < node.p.x())
                    || (!xCoordinate && p.y() < node.p.y())) {
                nearest(p, node.lb, depth + 1);
                nearest(p, node.rt, depth + 1);
            } else {
                nearest(p, node.rt, depth + 1);
                nearest(p, node.lb, depth + 1);
            }
        }
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
