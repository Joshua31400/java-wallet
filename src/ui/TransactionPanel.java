package ui;

import budgetmanager.Expense;
import budgetmanager.Income;
import budgetmanager.Transaction;
import service.BudgetService;

import javax.swing.*;
import java.awt.*;

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
            _ -> showTransactionPanel(),
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
            JOptionPane.showMessageDialog(this, "✅" + type + " added successfully!");
        }
    }

    private void showBalance() {
        double balance = budgetService.calculateBalance();
        JOptionPane.showMessageDialog(this, "💰 Current Balance: " + balance + "€");
    }

    private void showTransactionPanel() {
        new ShowTransaction(budgetService).setVisible(true);
    }

    private void removeTransaction() {
        String id = JOptionPane.showInputDialog(this, "Paste the transaction ID");
        budgetService.removeTransaction(id);
        JOptionPane.showMessageDialog(this, "Transaction deleted successfully!");
    }
}