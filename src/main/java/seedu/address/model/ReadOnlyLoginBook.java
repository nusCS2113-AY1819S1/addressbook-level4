package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.login.LoginDetails;

/**
 * Unmodifiable view of the login book
 */
public interface ReadOnlyLoginBook {

    /**
     * Returns an unmodifiable view of the accounts list.
     * This list will not contain any duplicate accounts.
     */
    ObservableList<LoginDetails> getLoginDetailsList();
}
