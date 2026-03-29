/**
 * Sudoku Solver — Backtracking Algorithm (Student Version)
 * Solves a 9x9 Sudoku puzzle using backtracking. Empty cells marked as 0.
 */

public class SudokuSolver {

    private static final int SIZE = 9;
    private static final int BOX_SIZE = 3;

    // HIGH-LEVEL: Find next empty (0) cell scanning left-to-right, top-to-bottom
    private static int[] findEmpty(int[][] board) {
        // Scan every cell in row-major order (row then column)
        // Return first position containing 0 as {row, column}
        // Return null when no empty cells remain (puzzle complete)
        return null;
    }

    // HIGH-LEVEL: Check if 'num' can legally go at board[row][col]
    private static boolean isSafe(int[][] board, int row, int col, int num) {
        // Check: num must not already appear in this row
        // Check: num must not already appear in this column  
        // Check: num must not already appear in this 3x3 box
        // Box starts at: row/3*3, col/3*3 (integer division)
        // Return true only if ALL three checks pass
        return true;
    }

    // HIGH-LEVEL: Solve puzzle by filling one cell at a time
    public static boolean solve(int[][] board) {
        // Step 1: Find next empty cell position
        // Step 2: If no empty cells left, puzzle solved ✓
        // Step 3: For this empty cell, try placing digits 1 through 9
        // Step 4: For each digit:
        //      - If safe to place: put it down, recurse to next cell
        //      - If recursion returns true: solution found!
        //      - If recursion fails: erase this digit (backtrack)
        // Step 5: If no digit works here: this path failed
        return false;
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < SIZE; i++) {
            if (i % BOX_SIZE == 0 && i != 0) 
                System.out.println("------+-------+------");
            
            for (int j = 0; j < SIZE; j++) {
                if (j % BOX_SIZE == 0 && j != 0) 
                    System.out.print(" | ");
                System.out.print(board[i][j] + (j == SIZE-1 ? "\n" : " "));
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Tiny 4x4 test first
        int[][] tinyBoard = {
            {1, 0, 0, 2},
            {0, 4, 3, 0},
            {0, 0, 1, 0},
            {3, 0, 0, 4}
        };
        
        System.out.println("Tiny 4x4 test:");
        printBoard(tinyBoard);
        
        if (solve(tinyBoard)) {
            System.out.println("Tiny solved!");
            printBoard(tinyBoard);
        }

        // Real 9x9 (uncomment after tiny works)
        /*
        int[][] board = {
            {5,3,0,0,7,0,0,0,0},
            {6,0,0,1,9,5,0,0,0},
            {0,9,8,0,0,0,0,6,0},
            {8,0,0,0,6,0,0,0,3},
            {4,0,0,8,0,3,0,0,1},
            {7,0,0,0,2,0,0,0,6},
            {0,6,0,0,0,0,2,8,0},
            {0,0,0,4,1,9,0,0,5},
            {0,0,0,0,8,0,0,7,9}
        };
        
        System.out.println("Real puzzle:");
        printBoard(board);
        if (solve(board)) {
            System.out.println("Solved!");
            printBoard(board);
        }
        */
    }
}