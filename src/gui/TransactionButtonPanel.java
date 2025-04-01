package gui;

import service.TransactionHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * This class creates a panel with buttons for various transaction operations.
 * Each button is associated with an action listener that performs the corresponding operation.
 */
public class TransactionButtonPanel extends JPanel {
    public TransactionButtonPanel(TransactionHandler transactionHandler) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton addIncomeButton = createButton("Add Income", "/icons/earnings.png", e -> transactionHandler.addTransaction("Income"), Color.WHITE);
        JButton addExpenseButton = createButton("Add Expense", "/icons/loss.png", e -> transactionHandler.addTransaction("Expense"), Color.WHITE);
        JButton showBalanceButton = createButton("Show Balance", "/icons/overspent.png", e -> transactionHandler.showBalance(), Color.WHITE);
        JButton showTransactionsButton = createButton("Show Transactions", "/icons/transaction.png", e -> transactionHandler.showTransactionPanel(), Color.WHITE);
        JButton removeTransactionButton = createButton("Delete Transaction", "/icons/quit.png", e -> transactionHandler.removeTransaction(), Color.WHITE);
        JButton quitButton = createButton("Quit", "/icons/cross.png", e -> System.exit(0), Color.RED);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(addIncomeButton, gbc);
        gbc.gridy++;
        add(addExpenseButton, gbc);
        gbc.gridy++;
        add(showBalanceButton, gbc);
        gbc.gridy++;
        add(showTransactionsButton, gbc);
        gbc.gridy++;
        add(removeTransactionButton, gbc);
        gbc.gridy++;
        add(quitButton, gbc);
    }
/**
    * Creates a button with the specified text, icon, action listener, and background color.
 */
    private JButton createButton(String text, String iconPath, ActionListener listener, Color bgColor) {
        JButton button = new JButton(text);
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(iconPath));
            Image scaledImg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImg));
            button.setHorizontalAlignment(SwingConstants.LEFT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        button.setBackground(bgColor);
        button.addActionListener(listener);
        return button;
    }
}