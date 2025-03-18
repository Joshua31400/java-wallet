package budgetmanager;

import java.time.LocalDate;

public class Expense extends Transaction {
    private String category;

    public Expense(String id, double amount, LocalDate date, String description, String category) {
        super(id, amount, date, description);
        this.category = category;
    }
    public String getCategory() {
        return category;
    }
}
