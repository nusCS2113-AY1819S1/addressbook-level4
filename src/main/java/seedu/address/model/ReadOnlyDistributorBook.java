package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.distributor.Distributor;


/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyDistributorBook {

    /**
     * Returns an unmodifiable view of the distributors list.
     * This list will not contain any duplicate distributors.
     */
    ObservableList<Distributor> getDistributorList();


}
