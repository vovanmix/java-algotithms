package com.singingcode.algorithms_part1.assignment3collinear;

import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private static final double EPSILON = 0.0000000001;
    private final Point[] points;

    // finds all line segments containing 4 or more points
    // FastCollinearPoints should work properly even if the input has 5 or more
    // collinear points.
    public FastCollinearPoints(Point[] points) {
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

    // the number of line segments
    public int numberOfSegments() {
        return segments().length;
    }

    // the line segments
    public LineSegment[] segments() {
        Arrays.sort(points);
        ArrayList<LineSegment> segments = new ArrayList<>();
        for (Point point : points) {
            Point[] sortedPoints = points.clone();
            Arrays.sort(sortedPoints, point.slopeOrder());

            double lastSlope = -1;
            int start = -1;
            int end = -1;
            for (int j = 0; j < sortedPoints.length; j++) {
                double currentSlope = sortedPoints[j].slopeTo(point);
                if (Math.abs(currentSlope - lastSlope) < EPSILON) end = j;

                if (Math.abs(currentSlope - lastSlope) >= EPSILON || j == sortedPoints.length - 1) {
                    /* critical - if starting point of this segment is smaller than point,
                       then this line segment is already in the collection */
                    if (end - start + 1 >= 3 && sortedPoints[start].compareTo(point) > 0) {
                        segments.add(new LineSegment(point, sortedPoints[end]));
                        break;
                    }
                    start = j;
                    lastSlope = currentSlope;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
