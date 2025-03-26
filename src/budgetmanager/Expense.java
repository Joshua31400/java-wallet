package budgetmanager;

public class Expense extends Transaction {
    public Expense(String id, double amount, String date, String description, String category) {
        super(id, amount, date, description, category, "Expense");
    }
}
