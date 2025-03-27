package ui;

import budgetmanager.Transaction;
import budgetmanager.Income;
import budgetmanager.Expense;
import service.BudgetService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ShowTransaction extends JFrame {
    private BudgetService budgetService;

    public ShowTransaction(BudgetService budgetService) {
        this.budgetService = budgetService;
        setTitle("Transactions");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        showTransactions();
    }

    private void showTransactions() {
        List<Transaction> transactions = budgetService.getTransactions();

        JPanel incomePanel = new JPanel();
        incomePanel.setLayout(new BoxLayout(incomePanel, BoxLayout.Y_AXIS));
        JPanel expensePanel = new JPanel();
        expensePanel.setLayout(new BoxLayout(expensePanel, BoxLayout.Y_AXIS));

        for (Transaction transaction : transactions) {
            JLabel transactionLabel = new JLabel(
                "<html>ID: " + transaction.getId() + "<br>" +
                "Amount: " + transaction.getAmount() + "€<br>" +
                "Date: " + transaction.getDate() + "<br>" +
                "Category: " + transaction.getCategory() + "<br>" +
                "Description: " + transaction.getDescription() + "</html>"
            );
            transactionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            if (transaction instanceof Income) {
                incomePanel.add(transactionLabel);
            } else if (transaction instanceof Expense) {
                expensePanel.add(transactionLabel);
            }
        }

        JPanel incomeContainer = new JPanel(new BorderLayout());
        JLabel incomeTitle = new JLabel("Income", SwingConstants.CENTER);
        incomeTitle.setFont(new Font("Arial", Font.BOLD, 16));
        incomeContainer.add(incomeTitle, BorderLayout.NORTH);
        incomeContainer.add(new JScrollPane(incomePanel), BorderLayout.CENTER);

        JPanel expenseContainer = new JPanel(new BorderLayout());
        JLabel expenseTitle = new JLabel("Expense", SwingConstants.CENTER);
        expenseTitle.setFont(new Font("Arial", Font.BOLD, 16));
        expenseContainer.add(expenseTitle, BorderLayout.NORTH);
        expenseContainer.add(new JScrollPane(expensePanel), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, incomeContainer, expenseContainer);
        splitPane.setResizeWeight(0.5);

        add(splitPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        BudgetService budgetService = new BudgetService("username");
        SwingUtilities.invokeLater(() -> new ShowTransaction(budgetService).setVisible(true));
    }
}