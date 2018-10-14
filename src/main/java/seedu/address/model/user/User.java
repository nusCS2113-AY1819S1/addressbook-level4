package seedu.address.model.user;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a user account.
 */
public class User {

    private final Username username;
    private final Password password;

    public User(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;
    }

<<<<<<< HEAD
    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }
=======
    public Username getUsername() { return username; }

    public Password getPassword() { return password; }
>>>>>>> 38598fe88fbbfc7ca04425ebd7a1e8e61909d7ae
}
