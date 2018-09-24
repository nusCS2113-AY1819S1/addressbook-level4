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

    public void setUserId(UserId id) {
        this.userId = id;
    }

    public void setUserPassword(UserPassword password) {
        this.userPassword = password;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameAccount(LoginDetails otherAccount) {
        if (otherAccount == this) {
            return true;
        }

        return otherAccount != null && otherAccount.getUserId().equals(getUserId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(userId, userPassword);
    }
}
