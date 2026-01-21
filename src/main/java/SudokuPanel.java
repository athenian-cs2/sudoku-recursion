import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * A Swing panel that displays the 9x9 Sudoku grid.
 */
public class SudokuPanel extends JPanel {
    private SudokuCell[][] cells;
    private SudokuBoard board;

    public SudokuPanel(SudokuBoard board) {
        this.board = board;
        this.cells = new SudokuCell[9][9];

        setLayout(new GridLayout(9, 9, 0, 0));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        initializeCells();
    }

    private void initializeCells() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                SudokuCell cell = new SudokuCell(row, col);
                cells[row][col] = cell;

                // Add document listener to sync cell changes with the board
                final int r = row;
                final int c = col;
                cell.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        updateBoard(r, c);
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        updateBoard(r, c);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        updateBoard(r, c);
                    }
                });

                add(cell);
            }
        }
    }

    private void updateBoard(int row, int col) {
        int value = cells[row][col].getValue();
        board.setValue(row, col, value);
    }

    public void loadBoard(SudokuBoard newBoard) {
        this.board = newBoard;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = board.getValue(row, col);
                boolean isFixed = board.isFixed(row, col);

                cells[row][col].setValue(value);
                cells[row][col].setFixed(isFixed);
            }
        }
    }

    public void clearUserInput() {
        board.clear();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!cells[row][col].isFixed()) {
                    cells[row][col].setValue(0);
                }
                cells[row][col].updateAppearance();
            }
        }
    }

    public void resetBoard() {
        board.reset();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].setValue(0);
                cells[row][col].setFixed(false);
            }
        }
    }

    public void showSolution() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = board.getValue(row, col);
                cells[row][col].setValue(value);
            }
        }
    }

    public void highlightErrors() {
        // Reset all cells first
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col].updateAppearance();
            }
        }

        // Highlight cells with invalid values
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = board.getValue(row, col);
                if (value != 0 && !board.isValidPlacement(row, col, value)) {
                    cells[row][col].highlightError();
                }
            }
        }
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public SudokuCell getCell(int row, int col) {
        return cells[row][col];
    }
}
