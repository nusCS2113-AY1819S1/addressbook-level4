package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.login.LoginDetails;

/**
 * Unmodifiable view of the login book
 */
public interface ReadOnlyLoginBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<LoginDetails> getLoginDetailsList();
}
