package ui.swing;

import java.awt.*;
import javax.swing.*;

/**
 * Login dialog for the Customer Segmentation GUI.
 * For now, accepts any non-empty admin username/password.
 * In production, this would validate against a user database.
 */
public class LoginDialog extends JDialog {
    private boolean authenticated = false;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginDialog(Frame parent) {
        super(parent, "Admin Login", true);

        // Slightly bigger dialog
        setSize(450, 260);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        // Main container
        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 🔹 Top: project name + "Admin Login"
        JPanel header = new JPanel(new GridLayout(2, 1));
        JLabel title = new JLabel("Customer Segmentation Dashboard", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));

        JLabel subtitle = new JLabel("Admin Login", SwingConstants.CENTER);
        subtitle.setFont(subtitle.getFont().deriveFont(Font.PLAIN, 14f));

        header.add(title);
        header.add(subtitle);
        content.add(header, BorderLayout.NORTH);

        // 🔹 Center: form (username + password)
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Username label + field
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        usernameField = new JTextField(20);
        form.add(usernameField, gbc);

        // Password label + field
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        form.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        form.add(passwordField, gbc);

        content.add(form, BorderLayout.CENTER);

        // 🔹 Bottom: buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton loginBtn = new JButton("Login");
        JButton cancelBtn = new JButton("Cancel");

        loginBtn.addActionListener(e -> handleLogin());
        cancelBtn.addActionListener(e -> {
            authenticated = false;
            dispose();
        });

        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);
        content.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(content);
        pack(); // resize nicely around components
        setLocationRelativeTo(getParent());
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter both username and password.",
                "Login Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // TODO: hook this up to real admin accounts later if you want
        authenticated = true;
        dispose();
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public static boolean showLogin(Frame parent) {
        LoginDialog dialog = new LoginDialog(parent);
        dialog.setVisible(true);
        return dialog.isAuthenticated();
    }
}
