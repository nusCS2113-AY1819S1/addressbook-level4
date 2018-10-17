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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isPresent;
    }

    public void logUser(User user) {
        this.user = user;
        loginStatus = true;
    }

    public void clearUser() {
        loginStatus = false;
    }
}
