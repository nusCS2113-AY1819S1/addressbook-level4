package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.budgetelements.ClubBudgetElements;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyClubBudgetElementsBook {

    /**
     * Returns an unmodifiable view of the club budget elements list.
     * This list will not contain any duplicate club budget elements.
     */
    ObservableList<ClubBudgetElements> getClubsList();


}
