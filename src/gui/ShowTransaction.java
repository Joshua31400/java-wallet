package gui;

import budgetmanager.Transaction;
import budgetmanager.Income;
import budgetmanager.Expense;
import service.BudgetService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 * ShowTransaction class displays a window with two panels: one for Income transactions and another for Expense transactions.
 * Each panel shows the details of the transactions in a scrollable area.
 */
public class ShowTransaction extends JFrame {
    public ShowTransaction(BudgetService budgetService, Component parent) {
        setTitle("Transactions");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        List<Transaction> transactions = budgetService.getTransactions();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                createTransactionPanel("Income", transactions, Income.class),
                createTransactionPanel("Expense", transactions, Expense.class));
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);

        setLocationRelativeTo(parent);
        setLocation(getX() + 20, getY() + 20);
    }

    /**
     * Creates a panel to display transactions of a specific type (Income or Expense).
     * Displays the transactions in a scrollable area with a title in two different container.
     */
    private JPanel createTransactionPanel(String title, List<Transaction> transactions, Class<? extends Transaction> type) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(48, 48, 48));

        transactions.stream()
                .filter(type::isInstance)
                .forEach(transaction -> {
                    JLabel transactionLabel = new JLabel(
                            String.format("<html>ID: %s<br>Amount: %.2f€<br>Date: %s<br>Category: %s<br>Description: %s</html>",
                                    transaction.getId(), transaction.getAmount(), transaction.getDate(),
                                    transaction.getCategory(), transaction.getDescription()));
                    transactionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                    transactionLabel.setForeground(Color.WHITE);
                    panel.add(transactionLabel);
                    panel.add(new JSeparator(SwingConstants.HORIZONTAL));
                });

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(new Color(48, 48, 48));
        JLabel panelTitle = new JLabel(title, SwingConstants.CENTER);
        panelTitle.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitle.setForeground(Color.WHITE);
        container.add(panelTitle, BorderLayout.NORTH);
        container.add(new JScrollPane(panel), BorderLayout.CENTER);

        return container;
    }

    public static void main(String[] args) {
        BudgetService budgetService = new BudgetService("username", "password");
        SwingUtilities.invokeLater(() -> new ShowTransaction(budgetService, null).setVisible(true));
    }
}