package service;

import budgetmanager.Transaction;
import budgetmanager.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;
    private SaveService saveService;

    public UserManager() {
        users = new HashMap<>();
    }
/**
     * Loads the user data from a JSON file.
     * If the file does not exist, it creates a new user.
     */
    public boolean authenticateUser(String username, String password) {
        try (FileReader reader = new FileReader("src/save/" + username + "_transactions.json")) {
            JsonObject userJson = JsonParser.parseReader(reader).getAsJsonObject();
            return userJson.get("password").getAsString().equals(password);
        } catch (IOException e) {
            return false;
        }
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public void createUser(String username, String password) {
        User newUser = new User(username, password);
        users.put(username, newUser);

        saveService = new SaveService(username, password);
        saveService.saveTransactions(new ArrayList<Transaction>());
    }
}