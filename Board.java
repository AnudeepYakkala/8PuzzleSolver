/* *****************************************************************************
 *  Name: Anudeep Yakkala
 *  Date:06/05/2019
 *  Description: This class initilizes a board for the 8-Puzzle problem and 
 allows users to find the possible moves from each board position. 
 **************************************************************************** */

import java.util.LinkedList;

public class Board {
    private int[][] blocks;
    private int n;

    public Board(int[][] blocks) {
        n = blocks.length;
        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return n;
    }
    
    public int hamming() {
        int counter = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * n + j + 1) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != 0) {
                    sum += Math.abs(i - ((blocks[i][j] - 1) / n));
                    sum += Math.abs(j - ((blocks[i][j] - 1) % n));
                }
            }
        }
        return sum;
    }

    public boolean isGoal() {
        boolean correct = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != i * n + j + 1 && blocks[i][j] != 0) {
                    correct = false;
                }
            }
        }
        return correct;
    }

    public Board twin() {
        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = blocks[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if ((newBoard[i][j] != 0) && (newBoard[i][j + 1] != 0)) {
                    int placeholder = newBoard[i][j];
                    newBoard[i][j] = newBoard[i][j + 1];
                    newBoard[i][j + 1] = placeholder;
                    return new Board(newBoard);
                }
            }
        }
        throw new RuntimeException();
    }

    public boolean equals(Object y) {
        if (y == null || !(y.getClass() == Board.class)) {
            return false;
        }
        if (((Board) y).dimension() != dimension()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (((Board)y).blocks[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[] spaceLocation() {
        int[] location = new int[2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    location[0] = i;
                    location[1] = j;
                    return location;
                }
            }
        }
        throw new RuntimeException();
    }

    private int[][] swap(int row, int col, int newRow, int newCol) {
        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = blocks[i][j];
            }
        }

        int placeHolder = newBoard[row][col];
        newBoard[row][col] = newBoard[newRow][newCol];
        newBoard[newRow][newCol] = placeHolder;
        return newBoard;
    }

    public Iterable<Board> neighbors() {
        LinkedList<Board> list = new LinkedList<Board>();
        int[] space = spaceLocation();
        if (space[0] + 1 < n) {
            list.add(new Board(swap(space[0], space[1], space[0] + 1,
                                    space[1])));
        }
        if (space[0] - 1 > -1) {
            list.add(new Board(swap(space[0], space[1], space[0] - 1,
                                    space[1])));
        }
        if (space[1] + 1 < n) {
            list.add(new Board(swap(space[0], space[1], space[0],
                                    space[1] + 1)));
        }
        if (space[1] - 1 > -1) {
            list.add(new Board(swap(space[0], space[1], space[0],
                                    space[1] - 1)));
        }
        return list;
    }

    public String toString() {
        String toReturn = dimension() + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                toReturn += blocks[i][j] + " ";
            }
            toReturn += "\n";
        }
        return toReturn;
    }



}
