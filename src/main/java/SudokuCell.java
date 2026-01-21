import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;

/**
 * A Swing component representing a single cell in the Sudoku grid.
 */
public class SudokuCell extends JTextField {
    private int row;
    private int col;
    private boolean isFixed;

    private static final Color FIXED_BACKGROUND = new Color(220, 220, 220);
    private static final Color EDITABLE_BACKGROUND = Color.WHITE;
    private static final Color SELECTED_BACKGROUND = new Color(200, 230, 255);
    private static final Color ERROR_BACKGROUND = new Color(255, 200, 200);
    private static final Color FIXED_FOREGROUND = Color.BLACK;
    private static final Color EDITABLE_FOREGROUND = new Color(0, 0, 150);

    public SudokuCell(int row, int col) {
        super();
        this.row = row;
        this.col = col;
        this.isFixed = false;

        setHorizontalAlignment(JTextField.CENTER);
        setFont(new Font("SansSerif", Font.BOLD, 24));
        setPreferredSize(new Dimension(50, 50));
        setBorder(createCellBorder(row, col));

        // Limit input to single digit 1-9
        ((AbstractDocument) getDocument()).setDocumentFilter(new SudokuDocumentFilter());

        updateAppearance();
    }

    private Border createCellBorder(int row, int col) {
        int top = (row % 3 == 0) ? 2 : 1;
        int left = (col % 3 == 0) ? 2 : 1;
        int bottom = (row == 8) ? 2 : 1;
        int right = (col == 8) ? 2 : 1;
        return BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setFixed(boolean fixed) {
        this.isFixed = fixed;
        setEditable(!fixed);
        updateAppearance();
    }

    public boolean isFixed() {
        return isFixed;
    }

    public int getValue() {
        String text = getText().trim();
        if (text.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setValue(int value) {
        if (value == 0) {
            setText("");
        } else {
            setText(String.valueOf(value));
        }
    }

    public void updateAppearance() {
        if (isFixed) {
            setBackground(FIXED_BACKGROUND);
            setForeground(FIXED_FOREGROUND);
        } else {
            setBackground(EDITABLE_BACKGROUND);
            setForeground(EDITABLE_FOREGROUND);
        }
    }

    public void highlightSelected() {
        if (!isFixed) {
            setBackground(SELECTED_BACKGROUND);
        }
    }

    public void highlightError() {
        setBackground(ERROR_BACKGROUND);
    }

    /**
     * Document filter to allow only single digits 1-9.
     */
    private static class SudokuDocumentFilter extends DocumentFilter {
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String newText = fb.getDocument().getText(0, fb.getDocument().getLength());
            newText = newText.substring(0, offset) + text + newText.substring(offset + length);

            if (newText.isEmpty() || (newText.length() == 1 && newText.matches("[1-9]"))) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs)
                throws BadLocationException {
            replace(fb, offset, 0, text, attrs);
        }
    }
}
