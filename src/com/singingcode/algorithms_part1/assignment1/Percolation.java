package com.singingcode.algorithms_part1.assignment1;

public class Percolation {
    private int[][][] grid;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.grid = new int[n][n][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.grid[i][j] = new int[]{-1, -1};
            }
        }
    }

    private void unionNeighbours(int row, int col) {
        if (row - 1 >= 0) {
            this.union(row - 1, col, row, col);
        }
        if (row + 1 < this.grid.length) {
            this.union(row + 1, col, row, col);
        }
        if (col - 1 >= 0) {
            this.union(row, col - 1, row, col);
        }
        if (col + 1 < this.grid.length) {
            this.union(row, col + 1, row, col);
        }
    }

    private boolean find(int i, int j, int row, int col) {
        if (!this.isOpen(i + 1, j + 1)
                || !this.isOpen(row + 1, col + 1)) {
            return false;
        }
        return this.findRoot(i, j) == this.findRoot(row, col);
    }

    private int[] findRoot(int i, int j) {
        if (this.grid[i][j][0] == i && this.grid[i][j][1] == j) {
            return this.grid[i][j];
        }
        return this.findRoot(this.grid[i][j][0], this.grid[i][j][1]);
    }

    private void union(int i, int j, int row, int col) {
        if (!this.find(i, j, row, col)) {
            this.grid[i][j][0] = row;
            this.grid[i][j][1] = col;
        }
    }

    private void checkBounds(int row, int col) {
        if (row < 0 || col < 0 || row >= this.grid.length
                || col >= this.grid.length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        row--;
        col--;
        checkBounds(row, col);
        this.grid[row][col][0] = row;
        this.grid[row][col][1] = col;
        this.unionNeighbours(row, col);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        row--;
        col--;
        checkBounds(row, col);
        return this.grid[row][col][0] != -1;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        row--;
        col--;
        checkBounds(row, col);
        if (!this.isOpen(row + 1, col + 1)) {
            return false;
        }
        for (int j = 0; j < this.grid.length; j++) {
            if (find(0, j, row, col)) {
                return true;
            }
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        int cnt = 0;
        for (int[][] row : this.grid) {
            for (int[] col : row) {
                if (col[0] != -1) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int j = 0; j < this.grid.length; j++) {
            if (this.isFull(this.grid.length, j + 1)) {
                return true;
            }
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