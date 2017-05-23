package com.singingcode.algorithms_part1.assignment1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF weightedUnion;
    private final int n;
    private final int size;
    private final boolean[] openings;
    private int openSites = 0;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = n;
        this.size = (n * n) + 2;
        weightedUnion = new WeightedQuickUnionUF(size);
        openings = new boolean[size];
    }

    private void unionNeighbours(int row, int col, int index) {
        int above = index - n;
        int below = index + n;
        int left = index - 1;
        int right = index + 1;
        if (above > 0 && openings[above]) weightedUnion.union(above, index);
        if (row < n && openings[below]) weightedUnion.union(below, index);
        if (col > 1 && openings[left]) weightedUnion.union(left, index);
        if (col < n && openings[right]) weightedUnion.union(right, index);
    }

    private void checkBounds(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    private int getIndex(int row, int col) {
        return (n * (row - 1)) + (col - 1) + 1;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBounds(row, col);
        if(isOpen(row, col)) return;
        int index = getIndex(row, col);
        openings[index] = true;
        openSites++;

        if (row - 1 == 0) weightedUnion.union(0, index);
        unionNeighbours(row, col, index);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        int index = getIndex(row, col);
        return openings[index];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        int index = getIndex(row, col);
        return weightedUnion.connected(0, index);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        int startingIndex = n * n - n + 1;
        for(int i = startingIndex; i <= n * n; i++){
            if(!openings[i]) continue;
            if(weightedUnion.connected(0, i)) return true;
        }

        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("start!");

        Percolation p = new Percolation(8);
        p.open(1, 3);
        p.open(1, 4);
        p.open(1, 5);

        p.open(2, 1);
        p.open(2, 4);
        p.open(2, 5);
        p.open(2, 6);
        p.open(2, 7);
        p.open(2, 8);

        p.open(3, 1);
        p.open(3, 2);
        p.open(3, 3);
        p.open(3, 6);
        p.open(3, 7);

        p.open(4, 3);
        p.open(4, 4);
        p.open(4, 6);
        p.open(4, 7);
        p.open(4, 8);

        p.open(5, 2);
        p.open(5, 3);
        p.open(5, 4);
        p.open(5, 6);
        p.open(5, 7);

        p.open(6, 2);
        p.open(6, 7);
        p.open(6, 8);

        p.open(7, 1);
        p.open(7, 3);
        p.open(7, 5);
        p.open(7, 6);
        p.open(7, 7);
        p.open(7, 8);

        p.open(8, 1);
        p.open(8, 2);
        p.open(8, 3);
        p.open(8, 4);
        p.open(8, 6);

        System.out.println("initialized!");

        System.out.println(p.percolates());
    }
}