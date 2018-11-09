package seedu.address.model.account;

import java.util.Objects;

/**
 * Represents a logged in Account in the model.
 */
public class LoggedInAccount {

    private Username username;
    private boolean loginStatus;

    public LoggedInAccount() {
        this.username = null;
        this.loginStatus = false;
    }

    public Username getUsername() {
        return username;
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }

    /**
     * Returns true if both items of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two items.
     */
    public boolean isSameAccount(LoggedInAccount otherAccount) {
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

        if (!(other instanceof LoggedInAccount)) {
            return false;
        }

        LoggedInAccount otherAccount = (LoggedInAccount) other;
        return (otherAccount.getUsername().fullUsername.toLowerCase()).equals(getUsername().fullUsername.toLowerCase());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUsername());
        return builder.toString();
    }

    public void setLoggedInUser(Username username) {
        this.username = username;
        this.loginStatus = true;
    }

    public void setLoggedOutStatus() {
        this.username = null;
        this.loginStatus = false;
    }
}
