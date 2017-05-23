package com.singingcode.algorithms_part1.assignment1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        double[] openSiteCount = new double[trials];
        this.trials = trials;
        double size = n * n;
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }
            openSiteCount[i] = (percolation.numberOfOpenSites() / size);
        }
        mean = StdStats.mean(openSiteCount);
        stddev = StdStats.stddev(openSiteCount);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean() - ((1.96) * stddev() / Math.sqrt(trials));
    }

    public double confidenceHi() {
        return mean() + ((1.96) * stddev() / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(200, 100);
//        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean =\t" + stats.mean());
        System.out.println("stddev =\t" + stats.stddev());
        System.out.println("95% confidence interval =\t" + stats.confidenceLo() + ", " + stats.confidenceHi());
    }
}
