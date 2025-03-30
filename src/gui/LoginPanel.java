package gui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private MainView mainView;
    private LoginForm loginForm;

    public LoginPanel(MainView mainView) {
        this.mainView = mainView;
        setLayout(new BorderLayout());

        loginForm = new LoginForm(mainView);
        add(loginForm, BorderLayout.CENTER);

        JLabel creditLabel = new JLabel("© GitHub: Joshua31400", SwingConstants.LEFT);
        creditLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        creditLabel.setOpaque(true);
        creditLabel.setBackground(Color.WHITE);
        creditLabel.setForeground(Color.BLACK);
        add(creditLabel, BorderLayout.SOUTH);
            }
        }
