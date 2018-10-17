package seedu.address.testutil;

import seedu.address.model.LoginBook;
import seedu.address.model.login.LoginDetails;

/**
 * A utility class to help with building Loginbook objects.
 * Example usage: <br>
 *     {@code LoginBook lb = new LoginBookBuilder().withLoginDetails("A1234567M", "zaq1xsw2cde3").build();}
 */
public class LoginBookBuilder {

    private LoginBook loginBook;

    public LoginBookBuilder() {
        loginBook = new LoginBook();
    }

    public LoginBookBuilder(LoginBook loginBook) {
        this.loginBook = loginBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public LoginBookBuilder withLoginDetails(LoginDetails loginDetails) {
        loginBook.createAccount(loginDetails);
        return this;
    }

    public LoginBook build() {
        return loginBook;
    }
}
