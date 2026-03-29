public class NQueens {

    static int N;

    // HIGH-LEVEL: Check if queen at board[row][col] safe from attacks left/diags
    static boolean isSafe(int[][] board, int row, int col) {
        // TODO: Check row left side (i < col)
        // TODO: Upper-left diagonal (i--, j-- from row,col)
        // TODO: Lower-left diagonal (i++, j-- from row,col)
        // Return true if all safe
        return true;  // Replace
    }

    // HIGH-LEVEL: Try place queen in column 'col'. Recurse col+1. Undo bad choices.
    static boolean solve(int[][] board, int col) {
        // BASE: if col >= N: all placed ✓ return true
        
        // For each row 0 to N-1:
        //     if isSafe(row, col):
        //         board[row][col] = 1  // place
        //         if solve(board, col+1): return true
        //         board[row][col] = 0  // undo
        
        // No row worked: return false
        return false;  // Replace
    }

    static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row)
                System.out.print(cell == 1 ? " Q " : " . ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        N = 4;  // Start small: 1,2,4 then 8
        int[][] board = new int[N][N];

        System.out.println("N=" + N + " Queens:");
        if (solve(board, 0))
            printBoard(board);
        else
            System.out.println("No solution.");
        
    }
}