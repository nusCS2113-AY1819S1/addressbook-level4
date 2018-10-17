package seedu.address.model.login;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;

/**
 * A list of users that enforces uniqueness between its elements and does not allow nulls.
 * A user is considered unique by comparing using {@code User#isSameUser(User)}. As such, adding and updating of
 * users uses User#isSameUser(User) for equality so as to ensure that the user being added or updated is
 * unique in terms of identity in the UniqueUsersList. However, the removal of a user uses User#equals(Object) so
 * as to ensure that the user with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see User#isSameUser(User)
 */
public class UniqueUsersList implements Iterable<User> {

    private final ObservableList<User> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent user as the given argument.
     */
    public boolean contains(User toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameUser);
    }

    /**
     * Adds a user to the list.
     *
     * @throws DuplicateUserException if the user to add is a duplicate of an existing user in the list.
     */
    public void add(User toAdd) throws DuplicateUserException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateUserException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the user {@code target} in the list with {@code editedUser}.
     *
     * @throws DuplicateUserException if the replacement is equivalent to another existing user in the list.
     * @throws UserNotFoundException if {@code target} could not be found in the list.
     */
    public void setUser(User target, User editedUser)
            throws UserNotFoundException {
        requireAllNonNull(target, editedUser);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new UserNotFoundException();
        }
        if (!target.isSameUser(editedUser) && contains(editedUser)) {
            throw new DuplicateUserException();
        }

        internalList.set(index, editedUser);
    }

    public User getUser(String username) {
        for (User user :internalList) {
            if (username.equals(user.getUsername().toString())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Removes the equivalent user from the list.
     *
     * @throws UserNotFoundException if no such user could be found in the list.
     */
    public void remove(User toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new UserNotFoundException();
        }
    }

    public void setUsers(UniqueUsersList replacement) {
        requireNonNull(replacement);
        this.internalList.setAll(replacement.internalList);
    }

    public void setUsers(List<User> users) throws DuplicateUserException {
        requireAllNonNull(users);
        if (!usersAreUnique(users)) {
            throw new DuplicateUserException();
        }

        internalList.setAll(users);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<User> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<User> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueUsersList
                && this.internalList.equals(((UniqueUsersList) other).internalList));
    }

    /**
     * Returns true if {@code persons} contains only unique users.
     */
    private boolean usersAreUnique(List<User> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSameUser(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
