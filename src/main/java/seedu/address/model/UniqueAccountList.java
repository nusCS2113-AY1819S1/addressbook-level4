package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of accounts that enforces uniqueness between its elements and does not allow nulls.
 * An account is considered unique by comparing using {@code LoginDetails#isSameAccount(LoginDetails)}. As such, adding and updating of
 * accounts uses LoginDetails#isSameAccount(LoginDetails) for equality so as to ensure that the account being added or updated is
 * unique in terms of details in the UniqueAccountList. However, the removal of an account uses LoginDetails#equals(Object) so
 * as to ensure that the account with exactly the same user ID and password will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see LoginDetails#isSameAccount(LoginDetails)
 */
public class UniqueAccountList implements Iterable<LoginDetails>{

    private final ObservableList<LoginDetails> internalLoginList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent account as the given argument.
     */
    public boolean contains(LoginDetails toCheck) {
        requireNonNull(toCheck);
        return internalLoginList.stream().anyMatch(toCheck::isSameAccount);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(LoginDetails toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccountException();
        }
        internalLoginList.add(toAdd);
    }

    @Override
    public Iterator<LoginDetails> iterator() {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAccountList // instanceof handles nulls
                && internalLoginList.equals(((UniqueAccountList) other).internalLoginList));
    }

    @Override
    public int hashCode() {
        return internalLoginList.hashCode();
    }
}
