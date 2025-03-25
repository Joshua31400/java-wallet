package ui;

import service.BudgetService;
import budgetmanager.Income;
import budgetmanager.Expense;
import budgetmanager.Transaction;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionPanel extends JPanel {
    private BudgetService budgetService;
    private DateTimeFormatter formatter;

    public TransactionPanel(BudgetService budgetService) {
        this.budgetService = budgetService;
        this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        setLayout(new BorderLayout());

        TransactionButtonPanel buttonPanel = new TransactionButtonPanel(
            e -> addTransaction("Income"),
            e -> addTransaction("Expense"),
            e -> showBalance(),
            e -> showTransactions(),
            e -> removeTransaction(),
            e -> System.exit(0)
        );

        add(buttonPanel, BorderLayout.CENTER);
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
            LocalDate date = LocalDate.parse(dateField.getText(), formatter);
            String category = categoryField.getText();
            String description = descriptionField.getText();

            Transaction transaction;
            if (type.equals("Income")) {
                transaction = new Income(id, amount, date, category, description);
            } else {
                transaction = new Expense(id, amount, date, category, description);
            }

            budgetService.addTransaction(transaction);
            JOptionPane.showMessageDialog(this, "✅ " + type + " added successfully!");
        }
    }

    private void showBalance() {
        double balance = budgetService.calculateBalance();
        JOptionPane.showMessageDialog(this, "💰 Current Balance: " + balance + "€");
    }

    private void showTransactions() {
        java.util.List<Transaction> transactions = budgetService.getTransactions();
        StringBuilder message = new StringBuilder();

        if (transactions.isEmpty()) {
            message.append("No transactions recorded.");
        } else {
            for (Transaction transaction : transactions) {
                message.append("ID: ").append(transaction.getId()).append("\n")
                        .append("Amount: ").append(transaction.getAmount()).append("€\n")
                        .append("Date: ").append(transaction.getDate().format(formatter)).append("\n")
                        .append("Category: ").append(transaction.getCategory()).append("\n")
                        .append("Description: ").append(transaction.getDescription()).append("\n")
                        .append("-----------------------------\n");
            }
        }

        JOptionPane.showMessageDialog(this, message.toString(), "List of Transactions", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeTransaction() {
        String id = JOptionPane.showInputDialog(this, "Paste the transaction ID");
        if (id != null && !id.isEmpty()) {
            budgetService.removeTransaction(id);
            JOptionPane.showMessageDialog(this, "Transaction deleted successfully!");
        }
    }
}