package seedu.address.request;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an BookInventory
 */
public interface ReadOnlyRequests {

    /**
     * Returns an unmodifiable view of the requests list.
     * This list will not contain any duplicate requests.
     */
    ObservableList<Request> getRequestList();

}
