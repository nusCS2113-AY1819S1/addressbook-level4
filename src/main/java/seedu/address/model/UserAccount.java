package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.storage.JsonUserStorage;

/**
 * Represents user account authentication
 */
public class UserAccount {

    private JsonUserStorage userStorage;
    private User user;
    private boolean loginStatus;
    private boolean adminStatus;

    public UserAccount() {
        final Path userFolderPath = Paths.get("data");
        final Path userFilePath = Paths.get("data", "users.json");
        final Username username = new Username("stub");
        final Password password = new Password("stub");
        user = new User(username, password);
        loginStatus = false;
        adminStatus = false;

        try {
            userStorage = new JsonUserStorage(userFolderPath, userFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if user exists in the JSON file.
     */
    public boolean userExists(User user) {
        String loggedUsername = user.getUsername().toString();
        String loggedPassword = user.getPassword().toString();
        boolean isPresent = false;

        try {
            JsonObject userAccounts = userStorage.getUserAccounts();

            if (userAccounts.has(loggedUsername)) {
                JsonElement password = userAccounts.get(loggedUsername);
                isPresent = loggedPassword.equals(password.getAsString());
                adminStatus = loggedUsername.equals("admin");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isPresent;
    }
    
    /**
     * Returns true if user is logged in.
     */
    public boolean authenticate() {
        return loginStatus;
    }

    /**
     * Returns true if admin is logged in.
     */
    public boolean getAdminStatus() {
        return adminStatus;
    }

    /**
     * Returns logged in user.
     */
    public User getUser(User user) {
        return user;
    }

    /**
     * Sets the current user.
     */
    public void logUser(User user) {
        this.user = user;
        loginStatus = true;
    }

    /**
     * Clears the current user.
     */
    public void clearUser() {
        loginStatus = false;
    }
}
