package service;

import budgetmanager.Transaction;
import budgetmanager.Income;
import budgetmanager.Expense;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

import java.util.UUID;

public class TransactionHandler {
    private BudgetService budgetService;
    private String username;
    private String password;

    public TransactionHandler(BudgetService budgetService, String username, String password) {
        this.budgetService = budgetService;
        this.username = username;
        this.password = password;
    }

    public void addTransaction(String type) {
        JTextField amountField = new JTextField();
        JFormattedTextField dateField = createFormattedDateField();
        JTextField categoryField = new JTextField();
        JTextField descriptionField = new JTextField();

        Object[] message = {
            type + " Amount:", amountField,
            "Date (DD/MM/YYYY):", dateField,
            "Category:", categoryField,
            "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Add " + type, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = generateRandomId();
            double amount = Double.parseDouble(amountField.getText());
            String date = dateField.getText();
            String category = categoryField.getText();
            String description = descriptionField.getText();

            Transaction transaction = type.equals("Income") ?
                new Income(id, amount, date, category, description) :
                new Expense(id, amount, date, category, description);

            budgetService.addTransaction(transaction);
            JOptionPane.showMessageDialog(null, "✅" + type + " added successfully!");
        }
    }

    private JFormattedTextField createFormattedDateField() {
        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new JFormattedTextField(dateFormatter);
    }

    private String generateRandomId() {
        return UUID.randomUUID().toString();
    }

    public void showBalance() {
        double balance = budgetService.calculateBalance();
        String color = balance < 0 ? "red" : "green";
        String message = String.format("<html>💰 Current Balance: <span style='color:%s;'>%.2f&nbsp;€</span></html>", color, balance);
        JOptionPane.showMessageDialog(null, message, "Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showTransactionPanel() {
        new gui.ShowTransaction(budgetService, null).setVisible(true);
    }

    public void removeTransaction() {
        String id = JOptionPane.showInputDialog(null, "Paste the transaction ID");
        budgetService.removeTransaction(id);
        JOptionPane.showMessageDialog(null, "Transaction deleted successfully!");
    }
}