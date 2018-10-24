package seedu.address.storage;

import java.io.IOException;

import com.google.gson.JsonObject;
import seedu.address.model.UserSession;

/**
 * Represents a storage for {@link UserSession}.
 */
public interface UserStorage {

    /**
     * Creates a new User account.
     */
    void createUser(String username, String password) throws IOException;

    /**
     * Returns a JsonObject containing user accounts.
     */
    JsonObject getUserAccounts() throws IOException;

}
