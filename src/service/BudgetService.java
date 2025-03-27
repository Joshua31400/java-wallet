package service;

import budgetmanager.Transaction;

import java.util.List;

public class BudgetService {
    private List<Transaction> transactions;
    private SaveService saveService;

    public BudgetService(String username) {
        saveService = new SaveService(username);
        transactions = saveService.loadTransactions();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        saveService.saveTransactions(transactions);
    }

    public void removeTransaction(String id) {
        transactions.removeIf(transaction -> transaction.getId().equals(id));
        saveService.saveTransactions(transactions);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public double calculateBalance() {
        double balance = 0;
        for (Transaction transaction : transactions) {
            if (transaction instanceof budgetmanager.Income) {
                balance += transaction.getAmount();
            } else if (transaction instanceof budgetmanager.Expense) {
                balance -= transaction.getAmount();
            }
        }
        return balance;
    }
}