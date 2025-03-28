package service;

import budgetmanager.Transaction;
import budgetmanager.Income;
import budgetmanager.Expense;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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

        int option = JOptionPane.showConfirmDialog(null, message, "Add " + type, JOptionPane.OK_CANCEL_OPTION);
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
            saveTransactionToFile(transaction);
            JOptionPane.showMessageDialog(null, "✅" + type + " added successfully!");
        }
    }

    /**
     * Saves the transaction to a JSON file.
        * If the file does not exist, it creates a new one.
     * @param transaction
     */
    public void saveTransactionToFile(Transaction transaction) {
        String filePath = "src/save/" + username + "_transactions.json";
        JSONObject userJson;
        JSONArray transactions;

        try (FileReader reader = new FileReader(filePath)) {
            JSONTokener tokener = new JSONTokener(reader);
            userJson = new JSONObject(tokener);
            transactions = userJson.getJSONArray("transactions");
        } catch (IOException e) {
            userJson = new JSONObject();
            userJson.put("username", username);
            userJson.put("password", password);
            transactions = new JSONArray();
        }

        boolean transactionExists = false;
        for (int i = 0; i < transactions.length(); i++) {
            JSONObject existingTransaction = transactions.getJSONObject(i);
            if (isTransactionEqual(existingTransaction, transaction)) {
                transactionExists = true;
                break;
            }
        }
        if (!transactionExists) {
            JSONObject transactionJson = new JSONObject();
            transactionJson.put("id", transaction.getId());
            transactionJson.put("amount", transaction.getAmount());
            transactionJson.put("date", transaction.getDate());
            transactionJson.put("category", transaction.getCategory());
            transactionJson.put("description", transaction.getDescription());
            transactionJson.put("type", transaction instanceof Income ? "Income" : "Expense");

            transactions.put(transactionJson);
        }

        userJson.put("transactions", transactions);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(userJson.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving transaction: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
/**
        * Compares an existing transaction with a new transaction to check if they are equal.
        * @param existingTransaction The existing transaction in JSON format.
        * @param newTransaction The new transaction to compare.
        * @return true if the transactions are equal, false otherwise.
 */
    private boolean isTransactionEqual(JSONObject existingTransaction, Transaction newTransaction) {
        return existingTransaction.getString("id").equals(newTransaction.getId()) ||
                (existingTransaction.getDouble("amount") == newTransaction.getAmount() &&
                        existingTransaction.getString("date").equals(newTransaction.getDate()) &&
                        existingTransaction.getString("description").equals(newTransaction.getDescription()));
    }

    public void showBalance() {
        double balance = budgetService.calculateBalance();
        String color = balance < 0 ? "red" : "green";
        String message = String.format("<html>💰 Current Balance: <span style='color:%s;'>%.2f&nbsp;€</span></html>", color, balance);
        JOptionPane.showMessageDialog(null, message, "Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showTransactionPanel() {
        new ui.ShowTransaction(budgetService, null).setVisible(true);
    }

    public void removeTransaction() {
        String id = JOptionPane.showInputDialog(null, "Paste the transaction ID");
        budgetService.removeTransaction(id);
        JOptionPane.showMessageDialog(null, "Transaction deleted successfully!");
    }
}