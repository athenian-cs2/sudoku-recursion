import javax.swing.*;
import java.awt.*;

/**
 * The main window for the Sudoku application.
 */
public class SudokuFrame extends JFrame {
    private SudokuPanel sudokuPanel;
    private SudokuBoard board;
    private SudokuSolver solver;

    // Sample puzzles
    private static final int[][] EASY_PUZZLE = {
        {5, 3, 0, 0, 7, 0, 0, 0, 0},
        {6, 0, 0, 1, 9, 5, 0, 0, 0},
        {0, 9, 8, 0, 0, 0, 0, 6, 0},
        {8, 0, 0, 0, 6, 0, 0, 0, 3},
        {4, 0, 0, 8, 0, 3, 0, 0, 1},
        {7, 0, 0, 0, 2, 0, 0, 0, 6},
        {0, 6, 0, 0, 0, 0, 2, 8, 0},
        {0, 0, 0, 4, 1, 9, 0, 0, 5},
        {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };

    private static final int[][] MEDIUM_PUZZLE = {
        {0, 0, 0, 6, 0, 0, 4, 0, 0},
        {7, 0, 0, 0, 0, 3, 6, 0, 0},
        {0, 0, 0, 0, 9, 1, 0, 8, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 5, 0, 1, 8, 0, 0, 0, 3},
        {0, 0, 0, 3, 0, 6, 0, 4, 5},
        {0, 4, 0, 2, 0, 0, 0, 6, 0},
        {9, 0, 3, 0, 0, 0, 0, 0, 0},
        {0, 2, 0, 0, 0, 0, 1, 0, 0}
    };

    private static final int[][] HARD_PUZZLE = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 3, 0, 8, 5},
        {0, 0, 1, 0, 2, 0, 0, 0, 0},
        {0, 0, 0, 5, 0, 7, 0, 0, 0},
        {0, 0, 4, 0, 0, 0, 1, 0, 0},
        {0, 9, 0, 0, 0, 0, 0, 0, 0},
        {5, 0, 0, 0, 0, 0, 0, 7, 3},
        {0, 0, 2, 0, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 4, 0, 0, 0, 9}
    };

    public SudokuFrame() {
        super("Sudoku");
        solver = new SudokuSolver();
        board = new SudokuBoard(EASY_PUZZLE);

        initializeUI();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));

        // Create the Sudoku panel
        sudokuPanel = new SudokuPanel(board);
        sudokuPanel.loadBoard(board);

        // Create control panel
        JPanel controlPanel = createControlPanel();

        // Create puzzle selection panel
        JPanel puzzlePanel = createPuzzlePanel();

        // Add components
        add(sudokuPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(puzzlePanel, BorderLayout.NORTH);

        // Add padding
        ((JComponent) getContentPane()).setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(e -> solvePuzzle());

        JButton checkButton = new JButton("Check");
        checkButton.addActionListener(e -> checkSolution());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearBoard());

        JButton hintButton = new JButton("Hint");
        hintButton.addActionListener(e -> giveHint());

        panel.add(solveButton);
        panel.add(checkButton);
        panel.add(clearButton);
        panel.add(hintButton);

        return panel;
    }

    private JPanel createPuzzlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JLabel label = new JLabel("Select Puzzle:");
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        JButton emptyButton = new JButton("Empty");

        easyButton.addActionListener(e -> loadPuzzle(EASY_PUZZLE));
        mediumButton.addActionListener(e -> loadPuzzle(MEDIUM_PUZZLE));
        hardButton.addActionListener(e -> loadPuzzle(HARD_PUZZLE));
        emptyButton.addActionListener(e -> {
            sudokuPanel.resetBoard();
            board = sudokuPanel.getBoard();
        });

        panel.add(label);
        panel.add(easyButton);
        panel.add(mediumButton);
        panel.add(hardButton);
        panel.add(emptyButton);

        return panel;
    }

    private void loadPuzzle(int[][] puzzle) {
        board = new SudokuBoard(puzzle);
        sudokuPanel.loadBoard(board);
    }

    private void solvePuzzle() {
        // First sync the current cell values to the board
        syncCellsToBoard();

        if (solver.solve(board)) {
            sudokuPanel.showSolution();
            JOptionPane.showMessageDialog(this,
                "Puzzle solved!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "This puzzle has no solution. Please check for errors.",
                "No Solution",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void checkSolution() {
        syncCellsToBoard();
        sudokuPanel.highlightErrors();

        if (board.isSolved()) {
            JOptionPane.showMessageDialog(this,
                "Congratulations! The puzzle is solved correctly!",
                "Correct",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Check if there are errors or just incomplete
            boolean hasErrors = false;
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    int value = board.getValue(row, col);
                    if (value != 0 && !board.isValidPlacement(row, col, value)) {
                        hasErrors = true;
                        break;
                    }
                }
                if (hasErrors) break;
            }

            if (hasErrors) {
                JOptionPane.showMessageDialog(this,
                    "There are errors in your solution. Incorrect cells are highlighted.",
                    "Errors Found",
                    JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "No errors so far, but the puzzle is not complete.",
                    "Keep Going",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void clearBoard() {
        sudokuPanel.clearUserInput();
    }

    private void giveHint() {
        syncCellsToBoard();

        // Find an empty cell
        int hintRow = -1, hintCol = -1;
        outer:
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board.getValue(row, col) == 0) {
                    hintRow = row;
                    hintCol = col;
                    break outer;
                }
            }
        }

        if (hintRow == -1) {
            JOptionPane.showMessageDialog(this,
                "The puzzle is already complete!",
                "Hint",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Solve a copy of the board to find the correct value
        int[][] gridCopy = board.getGridCopy();
        SudokuBoard tempBoard = new SudokuBoard(gridCopy);
        if (solver.solve(tempBoard)) {
            int correctValue = tempBoard.getValue(hintRow, hintCol);
            board.setValue(hintRow, hintCol, correctValue);
            sudokuPanel.getCell(hintRow, hintCol).setValue(correctValue);
            sudokuPanel.getCell(hintRow, hintCol).highlightSelected();
        } else {
            JOptionPane.showMessageDialog(this,
                "Cannot provide hint - puzzle may have errors.",
                "Hint",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void syncCellsToBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!board.isFixed(row, col)) {
                    int value = sudokuPanel.getCell(row, col).getValue();
                    board.setValue(row, col, value);
                }
            }
        }
    }
}
