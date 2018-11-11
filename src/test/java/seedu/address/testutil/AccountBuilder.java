package seedu.address.testutil;

import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;

/**
 * A utility class to help with building Item objects.
 */
public class AccountBuilder {

    public static final String DEFAULT_USERNAME = "admin";
    public static final String DEFAULT_PASSWORD = "admin";

    private Username username;
    private Password password;

    public AccountBuilder() {
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
    }

    /**
     * Initializes the ItemBuilder with the data of {@code accountToCopy}.
     */
    public AccountBuilder(Account accountToCopy) {
        username = accountToCopy.getUsername();
        password = accountToCopy.getPassword();
    }

    /**
     * Sets the {@code Username} of the {@code Account} that we are building.
     */
    public AccountBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Account} that we are building.
     */
    public AccountBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }


    public Account build() {
        return new Account(username, password);
    }

}
