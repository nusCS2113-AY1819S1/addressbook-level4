package seedu.address.model;

import seedu.address.model.user.User;
import seedu.address.storage.JsonUserStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserAccount {

    private JsonUserStorage userStorage;

    public UserAccount() {
        final Path userFilePath = Paths.get("data", "users.json");

        try {
            userStorage = new JsonUserStorage(userFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(User user) {
        return userStorage.userExists(user);
    }
}
