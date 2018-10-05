package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.login.exceptions.DuplicateAccountException;

/**
 * A list of accounts that enforces uniqueness between its elements and does not allow nulls.
 * An account is considered unique by comparing using {@code LoginDetails#isSameAccount(LoginDetails)}.
 * As such, adding and updating of accounts uses LoginDetails#isSameAccount(LoginDetails)
 * for equality so as to ensure that the account being added or updated is unique in terms of details
 * in the UniqueAccountList. However, the removal of an account uses LoginDetails#equals(Object) so
 * as to ensure that the account with exactly the same user ID and password will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see LoginDetails#isSameAccount(LoginDetails)
 */
public class UniqueAccountList implements Iterable<LoginDetails> {

    private final ObservableList<LoginDetails> internalLoginList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent account as the given argument.
     */
    public boolean contains(LoginDetails toCheck) {
        requireNonNull(toCheck);
        return internalLoginList.stream().anyMatch(toCheck::isSameAccount);
    }

    /**
     * Adds an account to the list.
     * The account must not already exist in the list.
     */
    public void add(LoginDetails toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAccountException();
        }
        internalLoginList.add(toAdd);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setLoginDetails(List<LoginDetails> loginDetails) {
        requireAllNonNull(loginDetails);
        if (!loginDetailsAreUnique(loginDetails)) {
            throw new DuplicateAccountException();
        }

        internalLoginList.setAll(loginDetails);
    }

    @Override
    public Iterator<LoginDetails> iterator() {
        return internalLoginList.iterator();
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

    public ObservableList<LoginDetails> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalLoginList);
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean loginDetailsAreUnique(List<LoginDetails> loginDetails) {
        for (int i = 0; i < loginDetails.size() - 1; i++) {
            for (int j = i + 1; j < loginDetails.size(); j++) {
                if (loginDetails.get(i).isSameAccount(loginDetails.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
