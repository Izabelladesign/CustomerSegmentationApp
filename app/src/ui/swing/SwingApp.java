package ui.swing;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point for the Swing-based UI.
 */
public class SwingApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // fall back to default
            }

            // Show login dialog first
            if (LoginDialog.showLogin(null)) {
                DashboardFrame frame = new DashboardFrame();
                frame.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}

