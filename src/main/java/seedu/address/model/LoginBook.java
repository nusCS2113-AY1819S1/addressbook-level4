package seedu.address.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UniqueAccountList;

public class LoginBook implements ReadOnlyLoginBook{

    private final UniqueAccountList accounts;

    public LoginBook() {
        accounts = new UniqueAccountList();
    }

    //// login-level operations

    /**
     * Returns true if an account with the same credentials as {@code loginDetails} exists in the login book.
     */
    public boolean hasLoginDetails(LoginDetails loginDetails) {
        requireNonNull(loginDetails);
        return accounts.contains(loginDetails);
    }

    /**
     * Adds an account to the login book.
     * The account must not already exist in the login book.
     */
    public void createAccount(LoginDetails l) {
        accounts.add(l);
    }

    //// util methods

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginBook // instanceof handles nulls
                && accounts.equals(((LoginBook) other).accounts));
    }

    @Override
    public int hashCode() {
        return accounts.hashCode();
    }

    @Override
    public ObservableList<LoginDetails> getLoginDetailsList() {
        return null;
    }
}
