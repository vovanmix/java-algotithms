package com.singingcode.algorithms_part1.assignment4_8puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private final MinPQ<SearchNode> queue;
    private final MinPQ<SearchNode> twinQueue;
    private final SearchNode solutionLastNode;

    private class SearchNode {
        private final Board board;
        private final int moves;
        private final int cost;
        private final SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.cost = moves + board.manhattan();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    // Since each search node records the previous search node to get there, you
    // can chase the pointers all the way back to the initial search node
    // (and consider them in reverse order).
    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }

        queue = new MinPQ<>(getComparator());
        twinQueue = new MinPQ<>(getComparator());

        queue.insert(new SearchNode(initial, 0, null));
        twinQueue.insert(new SearchNode(initial.twin(), 0, null));

        solutionLastNode = solve();
    }

    private Comparator<SearchNode> getComparator() {
        return new BoardComparator();
    }

    private class BoardComparator implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            return a.cost - b.cost;
        }
    }

    // To reduce unnecessary exploration of useless search nodes, when considering
    // the neighbors of a search node, don't enqueue a neighbor if its board is the
    // same as the board of the previous search node.
    private SearchNode solve() {
        SearchNode node, twinNode;
        while (!queue.isEmpty()) {
            node = queue.delMin();
            twinNode = twinQueue.delMin();

            if (node.board.isGoal()) {
                return node;
            }

            if (twinNode.board.isGoal()) {
                return null;
            }

            enqueue(twinQueue, twinNode);
            enqueue(queue, node);
        }
        return null;
    }

    private void enqueue(MinPQ<SearchNode> q, SearchNode node) {
        for (Board neighbor : node.board.neighbors()) {
            if (node.previous == null
                    || !node.previous.board.equals(neighbor)) {
                q.insert(new SearchNode(neighbor,
                        node.moves + 1, node));
            }
        }
    }

    // is the initial board solvable?
    // the current API requires you to detect infeasiblity in Solver by using
    // two synchronized A* searches (e.g., using two priority queues).
    public boolean isSolvable() {
        return solutionLastNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return solutionLastNode.moves;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    // Add the items you want to a Stack<Board> or Queue<Board> and return that.
    // Of course, your client code should not depend on whether the iterable
    // returned is a stack or queue (because it could be some any iterable).
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Stack<Board> solution = new Stack<>();
        for (SearchNode n = solutionLastNode; n != null; n = n.previous) {
            solution.push(n.board);
        }
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

// The constructor should throw a java.lang.IllegalArgumentException
// if passed a null argument.

// To avoid recomputing the Manhattan priority of a search node from scratch
// each time during various priority queue operations, pre-compute its value when
// you construct the search node; save it in an instance variable; and return the
// saved value as needed. This caching technique is broadly applicable: consider
// using it in any situation where you are recomputing the same quantity many times
// and for which computing that quantity is a bottleneck operation.
