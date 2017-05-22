package com.singingcode.algorithms_part1.assignment1;

public class Percolation {
//    Corner cases.  By convention, the row and column indices are integers
// between 1 and n, where (1, 1) is the upper-left site: Throw a
// java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(),
// or isFull() is outside its prescribed range. The constructor should
// throw a java.lang.IllegalArgumentException if n ≤ 0.

//    Performance requirements.  The constructor should take time
// proportional to n2; all methods should take constant time plus a
// constant number of calls to the union–find methods union(), find(),
// connected(), and count().

    private boolean[][] grid;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        this.grid = new boolean[n][n];
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        // union, rows only
        this.grid[row][col] = true;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.isFull(row, col);
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return this.grid[row][col];
    }

    // number of open sites
    public int numberOfOpenSites() {
        int cnt = 0;
        for (boolean[] row : this.grid) {
            for (boolean col : row) {
                cnt++;
            }
        }
        return cnt;
    }

    // does the system percolate?
    public boolean percolates() {

    }

    // test client (optional)
    public static void main(String[] args) {
    }
}