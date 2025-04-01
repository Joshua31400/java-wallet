package service;

import budgetmanager.Transaction;
import com.google.gson.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SaveService {
    private Gson gson;
    private String userFilePath;
    private String username;
    private String password;

    public SaveService(String username, String password) {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.userFilePath = "src/save/" + username + "_transactions.json";
        this.username = username;
        this.password = password;
    }
/**
     * Saves the transactions to a JSON file.
     * If the file already exists, it updates the existing data.
     * It also saves the username and password in the JSON file.
     * @param transactions List of transactions to save
     */
    public void saveTransactions(List<Transaction> transactions) {
        JsonObject userJson = new JsonObject();

        if (Files.exists(Paths.get(userFilePath))) {
            try (FileReader fileReader = new FileReader(userFilePath)) {
                JsonElement jsonElement = JsonParser.parseReader(fileReader);
                if (jsonElement.isJsonObject()) {
                    JsonObject existingData = jsonElement.getAsJsonObject();

                    if (existingData.has("username")) {
                        this.username = existingData.get("username").getAsString();
                    }
                    if (existingData.has("password")) {
                        this.password = existingData.get("password").getAsString();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        userJson.addProperty("username", this.username);
        userJson.addProperty("password", this.password);
        userJson.add("transactions", gson.toJsonTree(transactions));

        try (FileWriter fileWriter = new FileWriter(userFilePath)) {
            fileWriter.write(gson.toJson(userJson));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/**
     * Loads transactions from the JSON file.
     * If the file does not exist, it returns an empty list.
     * It also retrieves the username and password from the JSON file.
     * @return List of transactions
     */
    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        if (Files.exists(Paths.get(userFilePath))) {
            try (FileReader fileReader = new FileReader(userFilePath)) {
                JsonElement jsonElement = JsonParser.parseReader(fileReader);
                if (jsonElement.isJsonObject()) {
                    JsonObject userJson = jsonElement.getAsJsonObject();
                    if (userJson.has("username")) {
                        this.username = userJson.get("username").getAsString();
                    }
                    if (userJson.has("password")) {
                        this.password = userJson.get("password").getAsString();
                    }
                    if (userJson.has("transactions")) {
                        JsonArray transactionsArray = userJson.getAsJsonArray("transactions");
                        for (JsonElement element : transactionsArray) {
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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }
}