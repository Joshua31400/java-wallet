package service;

import budgetmanager.Transaction;
import budgetmanager.Income;
import budgetmanager.Expense;

import java.util.ArrayList;
import java.util.List;

public class BudgetService {

    private List<Transaction> transactions;

    public BudgetService() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeTransaction(String id) {
        transactions.removeIf(t -> t.getId().equals(id));
    }

    public double calculateBalance() {
        double balance = 0.0;

        for (Transaction transaction : transactions) {
            if (transaction instanceof Income) {
                balance += transaction.getAmount();
            } else if (transaction instanceof Expense) {
                balance -= transaction.getAmount();
            }
        }

        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Expense> getExpenses() {
        List<Expense> expenses = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction instanceof Expense) {
                expenses.add((Expense) transaction);
            }
        }
        return expenses;
    }

    public List<Income> getIncomes() {
        List<Income> incomes = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction instanceof Income) {
                incomes.add((Income) transaction);
            }
        }
        return incomes;
    }
}