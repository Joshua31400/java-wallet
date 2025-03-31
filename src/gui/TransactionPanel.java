package gui;

import service.TransactionHandler;
import service.BudgetService;

import javax.swing.*;
import java.awt.*;

public class TransactionPanel extends JPanel {
    private TransactionHandler transactionHandler;

    public TransactionPanel(BudgetService budgetService, String username, String password) {
        this.transactionHandler = new TransactionHandler(budgetService, username, password);
        setLayout(new BorderLayout());

        String capitalizedUsername = capitalizeFirstLetter(username);
        JLabel titleLabel = new JLabel("WELCOME " + capitalizedUsername, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        TransactionButtonPanel buttonPanel = new TransactionButtonPanel(transactionHandler);
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel, BorderLayout.CENTER);

        JLabel creditLabel = new JLabel("© GitHub: Joshua31400", SwingConstants.LEFT);
        creditLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        creditLabel.setOpaque(true);
        creditLabel.setBackground(Color.WHITE);
        add(creditLabel, BorderLayout.SOUTH);
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}