package budgetmanager;

import java.time.LocalDate;

public class Income extends Transaction {
    public Income(String id, double amount, LocalDate date, String category, String description) {
        super(id, amount, date, category, description);
    }

    @Override
    public String toString() {
        return "Income{" +
                "id='" + getId() + '\'' +
                ", amount=" + getAmount() +
                ", date=" + getDate() +
                ", category='" + getCategory() + '\'' +
                ", description='" + getDescription() + '\'' +
                '}';
    }
}