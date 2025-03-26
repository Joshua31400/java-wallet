package budgetmanager;

public class Income extends Transaction {
    public Income(String id, double amount, String date, String category, String description) {
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