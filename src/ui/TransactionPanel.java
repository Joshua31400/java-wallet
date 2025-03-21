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
            e -> addIncome(),
            e -> addExpense(),
            e -> showBalance(),
            e -> showTransactions(),
            e -> removeTransaction(),
            e -> System.exit(0)
        );

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addIncome() {
        JTextField idField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] message = {
            "Income ID:", idField,
            "Income Amount:", amountField,
            "Date (DD/MM/YYYY):", dateField,
            "Category:", categoryField,
            "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Income", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            double amount = Double.parseDouble(amountField.getText());
            LocalDate date = LocalDate.parse(dateField.getText(), formatter);
            String category = categoryField.getText();
            String description = descriptionField.getText();

            budgetService.addTransaction(new Income(id, amount, date, category, description));
            JOptionPane.showMessageDialog(this, "✅ Income added successfully!");
        }
    }

    private void addExpense() {
        JTextField idField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] message = {
            "Expense ID:", idField,
            "Expense Amount:", amountField,
            "Date (DD/MM/YYYY):", dateField,
            "Category:", categoryField,
            "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Expense", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            double amount = Double.parseDouble(amountField.getText());
            LocalDate date = LocalDate.parse(dateField.getText(), formatter);
            String category = categoryField.getText();
            String description = descriptionField.getText();

            budgetService.addTransaction(new Expense(id, amount, date, category, description));
            JOptionPane.showMessageDialog(this, "✅ Expense added successfully!");
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
                message.append(transaction.toString()).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, message.toString(), "📋 List of Transactions", JOptionPane.INFORMATION_MESSAGE);
    }

    private void removeTransaction() {
        String id = JOptionPane.showInputDialog(this, "ID of the transaction to delete:");
        if (id != null && !id.isEmpty()) {
            budgetService.removeTransaction(id);
            JOptionPane.showMessageDialog(this, "🗑️ Transaction deleted!");
        }
    }
}