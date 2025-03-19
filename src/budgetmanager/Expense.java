package budgetmanager;

import java.time.LocalDate;

public class Expense extends Transaction {
    public Expense(String id, double amount, LocalDate date, String category, String description) {
        super(id, amount, date, category, description);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + getId() + '\'' +
                ", amount=" + getAmount() +
                ", date=" + getDate() +
                ", category='" + getCategory() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}