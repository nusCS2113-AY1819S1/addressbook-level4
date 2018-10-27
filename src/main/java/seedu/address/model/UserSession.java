package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import seedu.address.commons.util.PasswordUtil;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;
import seedu.address.storage.JsonUserStorage;

/**
 * Represents user account authentication
 */
public class UserSession {

    private JsonUserStorage userStorage;
    private User user;
    private boolean loginStatus;
    private boolean adminStatus;

    public UserSession() {
        final Path userFilePath = Paths.get("users.json");
        final Username username = new Username("stub");
        final Password password = new Password("stub");
        user = new User(username, password);
        loginStatus = false;
        adminStatus = false;

        try {
            userStorage = new JsonUserStorage(userFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if user exists in the JSON file.
     */
    public boolean userExists(User user) {
        String loggedUsername = user.getUsername().toString();
        Map<String, String> userAccounts = userStorage.getUserAccounts();

        return userAccounts.containsKey(loggedUsername);
    }

    /**
     * Sets the current user.
     */
    public void logUser(User user) {
        String loggedUsername = user.getUsername().toString();
        String loggedPassword = user.getPassword().toString();
        Map<String, String> userAccounts = userStorage.getUserAccounts();

        if (userAccounts.containsKey(loggedUsername)) {
            boolean passwordsMatch = false;
            String storedPassword = userAccounts.get(loggedUsername);

            try {
                passwordsMatch = PasswordUtil.validatePassword(loggedPassword, storedPassword);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }

            if (passwordsMatch) {
                this.user = user;
                loginStatus = true;
                adminStatus = loggedUsername.equals("admin");
            }
        }
    }

    /**
     * Creates a new user profile in the JSON file.
     */
    public void createUser(User user) {
        String loggedUsername = user.getUsername().toString();
        String loggedPassword = user.getPassword().toString();

        try {
            String encryptedPassword = PasswordUtil.getEncryptedPassword(loggedPassword);
            userStorage.createUser(loggedUsername, encryptedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns logged in user.
     */
    public User getUser(User user) {
        return user;
    }

    /**
     * Clears the current user.
     */
    public void clearUser() {
        loginStatus = false;
    }

    /**
     * Returns true if user is logged in.
     */
    public boolean getLoginStatus() {
        return loginStatus;
    }

    /**
     * Returns true if admin is logged in.
     */
    public boolean getAdminStatus() {
        return adminStatus;
    }
}
