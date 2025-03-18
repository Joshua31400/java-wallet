package budgetmanager;

import java.time.LocalDate;

public class Income extends Transaction {
    private String category;

    public Income(String id, double amount, LocalDate date, String description, String category) {
        super(id, amount, date, description);
        this.category = category;
    }
    public String getCategory() {
        return category;
    }
}