package seedu.address.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import seedu.address.model.user.User;
import seedu.address.storage.JsonUserStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserAccount {

    private JsonUserStorage userStorage;

    public UserAccount() {
        final Path userFolderPath = Paths.get("data");
        final Path userFilePath = Paths.get("data", "users.json");

        try {
            userStorage = new JsonUserStorage(userFolderPath, userFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(User user) {
        String loggedUsername = user.getUsername().toString();
        String loggedPassword = user.getPassword().toString();
        boolean isPresent = false;

        try {
            JSONObject userAccounts = userStorage.getUserAccounts();
            String password = (String) userAccounts.get(loggedUsername);
            isPresent = password.equals(loggedPassword);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return isPresent;
    }
}
