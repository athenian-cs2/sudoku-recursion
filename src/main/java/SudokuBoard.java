/**
 * Represents a 9x9 Sudoku board.
 */
public class SudokuBoard {
    private int[][] grid;
    private boolean[][] fixed; // Tracks which cells are pre-filled (not editable)

    public SudokuBoard() {
        grid = new int[9][9];
        fixed = new boolean[9][9];
    }

    public SudokuBoard(int[][] initialGrid) {
        this();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (initialGrid[row][col] != 0) {
                    grid[row][col] = initialGrid[row][col];
                    fixed[row][col] = true;
                }
            }
        }
    }

    public int getValue(int row, int col) {
        return grid[row][col];
    }

    public void setValue(int row, int col, int value) {
        if (!fixed[row][col]) {
            grid[row][col] = value;
        }
    }

    public boolean isFixed(int row, int col) {
        return fixed[row][col];
    }

    public void setFixed(int row, int col, boolean isFixed) {
        fixed[row][col] = isFixed;
    }

    public boolean isValidPlacement(int row, int col, int num) {
        // Check row
        for (int c = 0; c < 9; c++) {
            if (c != col && grid[row][c] == num) {
                return false;
            }
        }

        // Check column
        for (int r = 0; r < 9; r++) {
            if (r != row && grid[r][col] == num) {
                return false;
            }
        }

        // Check 3x3 box
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                if ((r != row || c != col) && grid[r][c] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isSolved() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    return false;
                }
                if (!isValidPlacement(row, col, grid[row][col])) {
                    return false;
                }
            }
        }
        return true;
    }

    public void clear() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!fixed[row][col]) {
                    grid[row][col] = 0;
                }
            }
        }
    }

    public void reset() {
        grid = new int[9][9];
        fixed = new boolean[9][9];
    }

    public int[][] getGridCopy() {
        int[][] copy = new int[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, 9);
        }
        return copy;
    }
}
