/**
 * Solves Sudoku puzzles using recursive backtracking.
 */
public class SudokuSolver {

    /**
     * Attempts to solve the given Sudoku board using recursive backtracking.
     *
     * @param board the SudokuBoard to solve
     * @return true if the puzzle was solved, false if no solution exists
     */
    public boolean solve(SudokuBoard board) {
        return solveRecursive(board, 0, 0);
    }

    /**
     * Recursive helper method that attempts to solve the puzzle starting from
     * the given cell position.
     * Write code that tries out random numbers until it finds a solution.
     *
     * @param board the SudokuBoard to solve
     * @param row current row position
     * @param col current column position
     * @return true if solved successfully, false otherwise
     */
    private boolean solveRecursive(SudokuBoard board, int row, int col) {
        // Base case: if we've gone through the whole board, the puzzle is solved!

        // Find the next open cell position

        // Try each number 1-9, and recursively try to solve for each.
        // Backtrack if no solution that starts with this number can be found.
        // Note: the built-in isValidPlacement function will be very useful here!


        // No valid number found for this cell, need to backtrack
        return false;
    }

    /**
     * Checks if the given board has a valid solution.
     * This method does not modify the original board.
     *
     * @param board the SudokuBoard to check
     * @return true if a solution exists, false otherwise
     */
    public boolean hasSolution(SudokuBoard board) {
        // Create a copy of the grid to test
        int[][] gridCopy = board.getGridCopy();
        SudokuBoard testBoard = new SudokuBoard(gridCopy);
        return solve(testBoard);
    }

    /**
     * Counts the number of solutions for the given puzzle.
     * Useful for checking if a puzzle has a unique solution.
     *
     * @param board the SudokuBoard to analyze
     * @param maxCount stop counting after finding this many solutions
     * @return the number of solutions found (up to maxCount)
     */
    public int countSolutions(SudokuBoard board, int maxCount) {
        int[][] gridCopy = board.getGridCopy();
        return countSolutionsRecursive(gridCopy, 0, 0, maxCount);
    }

    private int countSolutionsRecursive(int[][] grid, int row, int col, int maxCount) {
        if (row == 9) {
            return 1;
        }

        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col == 8) ? 0 : col + 1;

        if (grid[row][col] != 0) {
            return countSolutionsRecursive(grid, nextRow, nextCol, maxCount);
        }

        int count = 0;
        for (int num = 1; num <= 9 && count < maxCount; num++) {
            if (isValidPlacement(grid, row, col, num)) {
                grid[row][col] = num;
                count += countSolutionsRecursive(grid, nextRow, nextCol, maxCount - count);
                grid[row][col] = 0;
            }
        }

        return count;
    }

    private boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        // Check row
        for (int c = 0; c < 9; c++) {
            if (grid[row][c] == num) {
                return false;
            }
        }

        // Check column
        for (int r = 0; r < 9; r++) {
            if (grid[r][col] == num) {
                return false;
            }
        }

        // Check 3x3 box
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                if (grid[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}
