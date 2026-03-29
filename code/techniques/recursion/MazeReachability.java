public class MazeReachability {

    // Fills all reachable cells from (row, col) with 'V' (visited)
    public static void fillReachable(char[][] maze, int row, int col) {
        // Bounds check
        if (row < 0 || row >= maze.length || col < 0 || col >= maze[0].length) {
            return;
        }
        
        // Stop: wall or already visited
        if (maze[row][col] == '#' || maze[row][col] == 'V') {
            return;
        }
        
        // Mark current as reachable
        maze[row][col] = 'V';
        
        // Explore 4 directions
        fillReachable(maze, row - 1, col);  // up
        fillReachable(maze, row + 1, col);  // down
        fillReachable(maze, row, col - 1);  // left
        fillReachable(maze, row, col + 1);  // right
    }

    public static void main(String[] args) {
        // Test maze:
        // . = open, # = wall, S = start
        char[][] maze1 = {
            {'S', '.', '#', '.'},
            {'#', '.', '.', '#'},
            {'.', '#', '.', '.'},
            {'#', '.', '#', '.'}
        };
        
        System.out.println("Maze 1 before:");
        printMaze(maze1);
        
        fillReachable(maze1, 0, 0);
        
        System.out.println("Maze 1 reachable (V):");
        printMaze(maze1);

        // Test 2: trapped start
        char[][] maze2 = {
            {'S', '#'},
            {'#', '#'}
        };
        
        System.out.println("\nMaze 2 before:");
        printMaze(maze2);
        
        fillReachable(maze2, 0, 0);
        
        System.out.println("Maze 2 reachable:");
        printMaze(maze2);
    }

    private static void printMaze(char[][] maze) {
        for (char[] row : maze) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}