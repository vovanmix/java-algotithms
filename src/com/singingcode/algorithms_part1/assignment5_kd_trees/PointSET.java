package com.singingcode.algorithms_part1.assignment5_kd_trees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> tree;

    // construct an empty set of points
    public PointSET() {
        tree = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    // number of points in the set
    public int size() {
        return tree.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) { throw new java.lang.IllegalArgumentException(); }
        tree.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) { throw new java.lang.IllegalArgumentException(); }
        return tree.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : tree) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) { throw new java.lang.IllegalArgumentException(); }

        ArrayList<Point2D> res = new ArrayList<>();
        for (Point2D p : tree) {
            if (p.x() < rect.xmin()) continue;
            if (p.x() > rect.xmax()) continue;
            if (p.y() < rect.ymin()) continue;
            if (p.y() > rect.ymax()) continue;

            res.add(p);
        }
        return res;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) { throw new java.lang.IllegalArgumentException(); }

        Point2D nearest = null;
        double distance = Double.MAX_VALUE;
        for (Point2D that : tree) {
            double newDistance = p.distanceSquaredTo(that);
            if (newDistance < distance) {
                nearest = that;
                distance = newDistance;
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        RectHV rect = new RectHV(0.1, 0.2, 0.3, 0.5);
        PointSET set = new PointSET();
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
