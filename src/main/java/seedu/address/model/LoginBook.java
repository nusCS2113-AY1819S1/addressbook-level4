package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.login.LoginDetails;
import seedu.address.model.login.UniqueAccountList;

/**
 * Wraps all data at the login-book level
 * Duplicates are not allowed (by .isSameAccount comparison)
 */
public class LoginBook implements ReadOnlyLoginBook {

    private final UniqueAccountList accounts;

    public LoginBook() {
        accounts = new UniqueAccountList();
    }

    /**
     * Creates a LoginBook using the LoginDetails in the {@code toBeCopied}
     */
    public LoginBook(ReadOnlyLoginBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setLoginDetails(List<LoginDetails> accounts) {
        this.accounts.setLoginDetails(accounts);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyLoginBook newData) {
        requireNonNull(newData);

        setLoginDetails(newData.getLoginDetailsList());
    }

    //// login-level operations

    /**
     * Returns true if an account with the same credentials as {@code loginDetails} exists in the login book.
     */
    public boolean hasAccount(LoginDetails loginDetails) {
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
    public String toString() {
        return accounts.asUnmodifiableObservableList().size() + " accounts";
    }

    @Override
    public int hashCode() {
        return accounts.hashCode();
    }

    @Override
    public ObservableList<LoginDetails> getLoginDetailsList() {
        return accounts.asUnmodifiableObservableList();
    }
}
