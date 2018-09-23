package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents user's login credentials.
 */
public class LoginDetails {
    private UserId userId;
    private UserPassword userPassword;

    public LoginDetails(UserId userId, UserPassword userPassword) {
        requireAllNonNull(userId, userPassword);
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public UserId getUserId() {
        return userId;
    }

    public UserPassword getUserPassword() {
        return userPassword;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(userId, userPassword);
    }
}
