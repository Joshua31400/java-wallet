package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    /*
     * Scale image to fit button.
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