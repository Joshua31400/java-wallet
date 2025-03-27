package ui;

import budgetmanager.User;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class LoginPanel extends JPanel {
    private Map<String, User> users;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private MainView mainView;

    public LoginPanel(MainView mainView) {
        this.mainView = mainView;
        users = new HashMap<>();
        // Add some users for testing
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));

        setLayout(new BorderLayout());

        JPanel loginForm = new JPanel(new GridBagLayout());
        loginForm.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginForm.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        loginForm.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginForm.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        loginForm.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.WHITE);
        loginButton.addActionListener(e -> login());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginForm.add(loginButton, gbc);

        add(loginForm, BorderLayout.CENTER);

        JLabel creditLabel = new JLabel("© GitHub: Joshua31400", SwingConstants.LEFT);
        creditLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        creditLabel.setOpaque(true);
        creditLabel.setBackground(Color.WHITE);
        creditLabel.setForeground(Color.BLACK);
        add(creditLabel, BorderLayout.SOUTH);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            mainView.showTransactionPanel(username);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}