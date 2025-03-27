package service;

import budgetmanager.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SaveService {
    private Gson gson;
    private String userFilePath;

    public SaveService(String username) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        userFilePath = "src/save/" + username + "_transactions.json";
    }

    public void saveTransactions(List<Transaction> transactions) {
        String json = gson.toJson(transactions);
        try (FileWriter fileWriter = new FileWriter(userFilePath)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads transactions from a JSON file for the current user.
     *
     * @return a list of transactions
     */
    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        if (Files.exists(Paths.get(userFilePath))) {
            try (FileReader fileReader = new FileReader(userFilePath)) {
                JsonElement jsonElement = JsonParser.parseReader(fileReader);
                if (jsonElement.isJsonArray()) {
                    for (JsonElement element : jsonElement.getAsJsonArray()) {
                        JsonObject jsonObject = element.getAsJsonObject();
                        String type = jsonObject.get("type").getAsString();
                        Transaction transaction = switch (type) {
                            case "Income" -> gson.fromJson(jsonObject, budgetmanager.Income.class);
                            case "Expense" -> gson.fromJson(jsonObject, budgetmanager.Expense.class);
                            default -> null;
                        };
                        if (transaction != null) {
                            transactions.add(transaction);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }
}