package seedu.address.model.account;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an Account in the stock list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Account {

    private final Username username;
    private final Password password;

    public Account(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    /**
     * Returns true if both items of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two items.
     */
    public boolean isSameAccount(Account otherAccount) {
        if (otherAccount == this) {
            return true;
        }

        return otherAccount != null
                && (otherAccount.getUsername().fullUsername.toLowerCase()).equals(getUsername().fullUsername
                .toLowerCase());
    }

    /**
     * Returns true if both items have the same identity and data fields.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Account)) {
            return false;
        }

        Account otherAccount = (Account) other;
        return (otherAccount.getUsername().fullUsername.toLowerCase()).equals(getUsername().fullUsername.toLowerCase());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUsername());
        return builder.toString();
    }
}
