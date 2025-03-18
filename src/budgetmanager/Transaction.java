package budgetmanager;

import java.time.LocalDate;

public abstract class Transaction {
    private String id;
    private double amount;
    private LocalDate date;
    private String description;

    public Transaction(String id, double amount, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
}