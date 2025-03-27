package ui;

import service.BudgetService;
import budgetmanager.Transaction;
import budgetmanager.Income;
import budgetmanager.Expense;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TransactionPanel extends JPanel {
    private BudgetService budgetService;

    public TransactionPanel(BudgetService budgetService, String username) {
        this.budgetService = budgetService;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("WELCOME " + username, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        TransactionButtonPanel buttonPanel = new TransactionButtonPanel(
            _ -> addTransaction("Income"),
            _ -> addTransaction("Expense"),
            _ -> showBalance(),
            _ -> showTransactions(),
            _ -> removeTransaction(),
            _ -> System.exit(0)
        );
        buttonPanel.setBackground(Color.BLACK);
        add(buttonPanel, BorderLayout.CENTER);

        JLabel creditLabel = new JLabel("© GitHub: Joshua31400", SwingConstants.LEFT);
        creditLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        creditLabel.setOpaque(true);
        creditLabel.setBackground(Color.WHITE);
        add(creditLabel, BorderLayout.SOUTH);
    }

    private void addTransaction(String type) {
        JTextField idField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] message = {
            type + " ID:", idField,
            type + " Amount:", amountField,
            "Date (DD/MM/YYYY):", dateField,
            "Category:", categoryField,
            "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add " + type, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String date = dateField.getText();
            String category = categoryField.getText();
            String description = descriptionField.getText();

            Transaction transaction = type.equals("Income") ?
                new Income(id, amount, date, category, description) :
                new Expense(id, amount, date, category, description);

            budgetService.addTransaction(transaction);
            JOptionPane.showMessageDialog(this, "✅ " + type + " added successfully!");
        }
    }

    private void showBalance() {
        double balance = budgetService.calculateBalance();
        JOptionPane.showMessageDialog(this, "💰 Current Balance: " + balance + "€");
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
        splitPane.setDividerLocation(300);

        JFrame frame = new JFrame("Transactions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.add(splitPane);
        frame.setVisible(true);
    }

    private void removeTransaction() {
        String id = JOptionPane.showInputDialog(this, "Paste the transaction ID");
        budgetService.removeTransaction(id);
        JOptionPane.showMessageDialog(this, "Transaction deleted successfully!");
    }
}