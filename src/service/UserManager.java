package service;

import budgetmanager.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, User> users;

    public UserManager() {
        users = new HashMap<>();
    }
    /**
    ** Load users from JSON files in the save directory.
     */
    public boolean authenticateUser(String username, String password) {
        try (FileReader reader = new FileReader("src/save/" + username + "_transactions.json")) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject userJson = new JSONObject(tokener);

            return userJson.getString("password").equals(password);
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

        JSONObject userJson = new JSONObject();
        userJson.put("username", username);
        userJson.put("password", password);
        userJson.put("transactions", new JSONArray());

        saveToFile(username, userJson);
    }

    private void saveToFile(String username, JSONObject userJson) {
        String filePath = "src/save/" + username + "_transactions.json";
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(userJson.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}