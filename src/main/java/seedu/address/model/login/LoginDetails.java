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
    private final UserRole userRole;

    /**
     * Every field must be present and not null.
     */
    public LoginDetails(UserId userId, UserPassword userPassword, UserRole userRole) {
        requireAllNonNull(userId, userPassword, userRole);
        this.userId = userId;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    public UserId getUserId() {
        return userId;
    }

    public UserPassword getUserPassword() {
        return userPassword;
    }

    public UserRole getUserRole() {
        return userRole;
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
        return Objects.hash(userId, userPassword, userRole);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUserId())
                .append(getUserPassword())
                .append(getUserRole());
        return builder.toString();
    }
}
