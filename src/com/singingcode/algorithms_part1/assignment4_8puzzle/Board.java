package com.singingcode.algorithms_part1.assignment4_8puzzle;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private final int[] blocks;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        int k = 0;
        this.blocks = new int[blocks.length * blocks.length];
        for (int[] row : blocks) {
            for (int block : row) {
                this.blocks[k] = block;
                k++;
            }
        }
    }

    // board dimension n
    public int dimension() {
        return (int) Math.sqrt((double) blocks.length);
    }

    // number of blocks out of place
    public int hamming() {
        int num = 0;
        int position = 0;
        for (int block : blocks) {
            if (block != 0) {
                int correctPosition = block - 1;
                if (position != correctPosition) {
                    num++;
                }
            }
            position++;
        }
        return num;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sum = 0;
        int position = 0;
        for (int block : blocks) {
            if (block != 0) {
                int correctPosition = block - 1;
                int diff = Math.abs(position - correctPosition);
                int manhattan = getRows(diff) + getCols(diff);
                sum += manhattan;
            }
            position++;
        }
        return sum;
    }

    private int getRows(int idx) {
        return (int) Math.floor((double) idx / (double) dimension());
    }

    private int getCols(int idx) {
        return Math.floorMod(idx, dimension());
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    // use it to determine whether a puzzle is solvable: exactly one of a board and
    // its twin are solvable. A twin is obtained by swapping any pair of blocks
    // (the blank square is not a block). For example, here is a board and several
    // possible twins. Your solver will use only one twin.
    public Board twin() {
        int[] swapped = blocks.clone();
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                continue;
            }
            for (int j = i + 1; j < blocks.length; j++) {
                if (blocks[j] != 0) {
                    swap(swapped, i, j);
                    break;
                }
            }
        }

        return makeBoard(swapped);
    }

    private Board makeBoard(int[] blocks) {
        int n = dimension();
        int[][] newBlocks = new int[n][n];
        int r = 0, c = 0;
        for (int block : blocks) {
            newBlocks[r][c] = block;
            c++;
            if (c >= n) {
                r++;
                c = 0;
            }
        }
        return new Board(newBlocks);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y == this)
            return true;
        if (y.getClass() != getClass())
            return false;
        Board that = (Board) y;
        if (that.dimension() != dimension())
            return false;

        for (int i = 0; i < blocks.length; i++) {
            if (that.blocks[i] != blocks[i])
                return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Set<Board> set = new HashSet<Board>();
        int i = -1;
        for (i = 0; i < blocks.length; i++) {
            if (blocks[i] == 0) {
                break;
            }
        }

        // not top row
        if (getRows(i) > 0) {
            int[] c = blocks.clone();
            swap(c, i, above(i));
            set.add(makeBoard(c));
        }

        // not bottom row
        if (getRows(i) < dimension() - 1) {
            int[] c = blocks.clone();
            swap(c, i, below(i));
            set.add(makeBoard(c));
        }

        // not left row
        if (getCols(i) > 0) {
            int[] c = blocks.clone();
            swap(c, i, left(i));
            set.add(makeBoard(c));
        }

        // not right row
        if (getCols(i) < dimension() - 1) {
            int[] c = blocks.clone();
            swap(c, i, right(i));
            set.add(makeBoard(c));
        }

        return set;
    }

    private int above(int idx) {
        return idx - dimension();
    }

    private int below(int idx) {
        return idx + dimension();
    }

    private static int left(int idx) {
        return idx - 1;
    }

    private static int right(int idx) {
        return idx + 1;
    }

    private static void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        int n = dimension();
        s.append(n).append("\n");
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[k]));
                k++;
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        System.out.println(args);
    }
}

// You may assume that the constructor receives an n-by-n array containing the
// n2 integers between 0 and n2 − 1, where 0 represents the blank square.

// Your implementation should support all Board methods in time proportional to
// n2 (or better) in the worst case.
