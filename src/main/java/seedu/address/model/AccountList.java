package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.account.Account;
import seedu.address.model.account.UniqueAccountList;

/**
 * Wraps all data at the stock-list level
 * Duplicates are not allowed (by .isSameItem comparison)
 */
public class AccountList implements ReadOnlyAccountList {

    private final UniqueAccountList accounts;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        accounts = new UniqueAccountList();
    }

    public AccountList() {}

    /**
     * Creates an AccountList using the Accounts in the {@code toBeCopied}
     */
    public AccountList(ReadOnlyAccountList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the account list with {@code accounts}.
     * {@code accounts} must not contain duplicate accounts.
     */
    public void setAccounts(List<Account> accounts) {
        this.accounts.setAccounts(accounts);
    }

    /**
     * Resets the existing data of this {@code AccountList} with {@code newData}.
     */
    public void resetData(ReadOnlyAccountList newData) {
        requireNonNull(newData);

        setAccounts(newData.getAccountList());
    }

    //// account-level operations

    /**
     * Returns true if an account with the same identity as {@code account} exists in the account list.
     */
    public boolean hasAccount(Account account) {
        requireNonNull(account);
        return accounts.contains(account);
    }

    /**
     * Adds an account to the account list.
     * The account must not already exist in the account list.
     */
    public void addAccount(Account p) {
        accounts.add(p);
    }

    /**
     * Replaces the given account {@code target} in the list with {@code editedAccount}.
     * {@code target} must exist in the account list.
     * The item identity of {@code editedAccount} must not be the same as another existing account in the account list.
     */
    public void updateAccount(Account target, Account editedAccount) {
        requireNonNull(editedAccount);

        accounts.setAccount(target, editedAccount);
    }

    /**
     * Removes {@code key} from this {@code AccountList}.
     * {@code key} must exist in the account list.
     */
    public void removeAccount(Account key) {
        accounts.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return accounts.asUnmodifiableObservableList().size() + " accounts";
        // TODO: refine later
    }

    @Override
    public ObservableList<Account> getAccountList() {
        return accounts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountList // instanceof handles nulls
                && accounts.equals(((AccountList) other).accounts));
    }

    @Override
    public int hashCode() {
        return accounts.hashCode();
    }
}
