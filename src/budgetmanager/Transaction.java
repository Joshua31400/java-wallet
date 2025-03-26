package budgetmanager;

public abstract class Transaction {
    private String id;
    private double amount;
    private String date;
    private String description;
    private String category;

    public Transaction(String id, double amount, String date, String description, String category) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }
}