package budgetmanager;

public class Income extends Transaction {
    public Income(String id, double amount, String date, String description, String category) {
        super(id, amount, date, description, category, "Income");
    }
}