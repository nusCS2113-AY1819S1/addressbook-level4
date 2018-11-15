package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.login.User;

/**
 * Unmodifiable view of the user database
 */
public interface ReadOnlyUserDatabase {

    /**
     * Returns an unmodifiable view of the users list.
     * This list will not contain any duplicate user.
     */

    ObservableList<User> getUsersList(); }
