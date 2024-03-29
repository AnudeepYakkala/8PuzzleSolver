/* *****************************************************************************
 *  Name: Anudeep Yakkala   
 *  Date: 06/05/2019
 *  Description: This class takes a text file representation of an 8-Puzzle
 board and prints the solution if the board state is solvable. 
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Move lastMove;

    private class Move implements Comparable<Move> {
        private Move previous;
        private Board board;
        private int numMoves = 0;

        public Move(Board board) {
            this.board = board;
        }

        public Move(Board board, Move previous) {
            this.board = board;
            this.previous = previous;
            this.numMoves = previous.numMoves + 1;
        }

        public int compareTo(Move move) {
            return (this.board.manhattan() - move.board.manhattan()) +
                    (this.numMoves - move.numMoves);
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }
        MinPQ<Move> moves = new MinPQ<Move>();
        moves.insert(new Move(initial));

        MinPQ<Move> twins = new MinPQ<Move>();
        twins.insert(new Move(initial.twin()));

        while (true) {
            lastMove = process(moves);
            if (lastMove != null || process(twins) != null) {
                return;
            }
        }

    }

    private Move process(MinPQ<Move> moves) {
        if (moves.isEmpty()) {
            return null;
        }
        Move bestMove = moves.delMin();
        if (bestMove.board.isGoal()) {
            return bestMove;
        }
        for (Board neighbor: bestMove.board.neighbors()) {
            if (bestMove.previous == null ||
                    !neighbor.equals(bestMove.previous.board)) {
                moves.insert(new Move(neighbor, bestMove));
            }
        }
        return null;
    }

    public boolean isSolvable() {
        return (lastMove != null);
    }

    public int moves() {
        if (isSolvable()) {
            return lastMove.numMoves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        Stack<Board> moves = new Stack<Board>();
        Move tempLastMove = new Move(lastMove.board, lastMove);
        tempLastMove = tempLastMove.previous;
        while (tempLastMove != null) {
            moves.push(tempLastMove.board);
            tempLastMove = tempLastMove.previous;
        }
        return moves;
    }

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
