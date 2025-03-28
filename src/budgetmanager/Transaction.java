package budgetmanager;

public abstract class Transaction {
    private String id;
    private double amount;
    private String date;
    private String description;
    private String category;
    private String type;

    public Transaction(String id, double amount, String date, String description, String category, String type) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
        this.type = type;
    }

    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public String getId() { return id; }
    public String getCategory() { return category; }
}