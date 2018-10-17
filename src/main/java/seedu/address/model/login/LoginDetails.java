package seedu.address.model.login;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents user's login credentials.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class LoginDetails {
    private final UserId userId;
    private final UserPassword userPassword;

    /**
     * Every field must be present and not null.
     */
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

    /**
     * Returns true if and only if both accounts are of the same user ID.
     */
    public boolean isSameAccount(LoginDetails otherAccount) {
        if (otherAccount == this) {
            return true;
        }

        return otherAccount != null && otherAccount.getUserId().equals(getUserId());
    }

    /**
     * Returns true if both accounts have the same user ID.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoginDetails)) {
            return false;
        }

        LoginDetails otherAccount = (LoginDetails) other;
        return otherAccount.getUserId().equals(getUserId());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(userId, userPassword);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUserId())
                .append(getUserPassword());
        return builder.toString();
    }
}
