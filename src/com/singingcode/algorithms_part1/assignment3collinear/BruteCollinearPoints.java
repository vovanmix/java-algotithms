package com.singingcode.algorithms_part1.assignment3collinear;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private static final double EPSILON = 0.0000000001;
    private final Point[] points;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException();
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }

        this.points = points.clone();
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        ArrayList<LineSegment> segments = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double slope1 = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length; k++) {
                    double slope2 = points[j].slopeTo(points[k]);
                    if (Math.abs(slope1 - slope2) >= EPSILON) continue;

                    for (int m = k + 1; m < points.length; m++) {
                        double slope3 = points[k].slopeTo(points[m]);
                        if (Math.abs(slope2 - slope3) >= EPSILON) continue;

                        Point[] tuple = new Point[] {points[i], points[j], points[k], points[m]};
                        Arrays.sort(tuple);
                        segments.add(new LineSegment(tuple[0], tuple[3]));
                    }
                }
            }
        }
        LineSegment[] segments2 = new LineSegment[segments.size()];
        return segments.toArray(segments2);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}