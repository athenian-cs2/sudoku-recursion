import javax.swing.*;

/**
 * Entry point for the Sudoku application.
 */
public class Main {
    public static void main(String[] args) {
        // Run on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for a native appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Fall back to default look and feel
            }

            SudokuFrame frame = new SudokuFrame();
            frame.setVisible(true);
        });
    }
}
