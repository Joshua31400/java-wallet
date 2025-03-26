package service;

import budgetmanager.Expense;
import budgetmanager.Income;
import budgetmanager.Transaction;
import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveService {
    private static final String FILE_PATH = "src/save/transactions.json";
    private Gson gson;

    public SaveService() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

/*
* Convert transactions list to JSON.
 */
    public void saveTransactions(List<Transaction> transactions) {
        String json = gson.toJson(transactions);
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
* Parse JSON from file.
* Deserialize b based on type.
 */
    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (FileReader fileReader = new FileReader(FILE_PATH)) {
            JsonElement jsonElement = JsonParser.parseReader(fileReader);
            if (jsonElement.isJsonArray()) {
                for (JsonElement element : jsonElement.getAsJsonArray()) {
                    JsonObject jsonObject = element.getAsJsonObject();
                    String type = jsonObject.get("type").getAsString();
                    Transaction transaction = null;
                    if ("Income".equals(type)) {
                        transaction = gson.fromJson(jsonObject, Income.class);
                    } else if ("Expense".equals(type)) {
                        transaction = gson.fromJson(jsonObject, Expense.class);
                    }
                    if (transaction != null) {
                        transactions.add(transaction);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}