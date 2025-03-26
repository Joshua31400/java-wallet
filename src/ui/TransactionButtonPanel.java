package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TransactionButtonPanel extends JPanel {
    public TransactionButtonPanel(ActionListener addIncomeListener, ActionListener addExpenseListener,
                                  ActionListener showBalanceListener, ActionListener showTransactionsListener,
                                  ActionListener removeTransactionListener, ActionListener quitListener) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton addIncomeButton = createButton("Add Income", "/icons/earnings.png", addIncomeListener, Color.WHITE);
        JButton addExpenseButton = createButton("Add Expense", "/icons/loss.png", addExpenseListener, Color.WHITE);
        JButton showBalanceButton = createButton("Show Balance", "/icons/overspent.png", showBalanceListener, Color.WHITE);
        JButton showTransactionsButton = createButton("Show Transactions", "/icons/transaction.png", showTransactionsListener, Color.WHITE);
        JButton removeTransactionButton = createButton("Delete Transaction", "/icons/quit.png", removeTransactionListener, Color.WHITE);
        JButton quitButton = createButton("Quit", "/icons/cross.png", quitListener, Color.RED);

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

    private JButton createButton(String text, String iconPath, ActionListener listener, Color bgColor) {
        JButton button = new JButton(text);
        java.net.URL imgURL = getClass().getResource(iconPath);
        if (imgURL != null) {
            try {
                BufferedImage img = ImageIO.read(imgURL);
                if (img != null) {
                    Image scaledImg = img.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledImg));
                    button.setHorizontalAlignment(SwingConstants.LEFT);
                } else {
                    System.err.println("Couldn't load image: " + iconPath);
                }
            } catch (IOException e) {
                System.err.println("Couldn't load file: " + iconPath);
            }
        } else {
            System.err.println("Couldn't find file: " + iconPath);
        }
        if (bgColor != null) {
            button.setBackground(bgColor);
        }
        button.addActionListener(listener);
        return button;
    }
}