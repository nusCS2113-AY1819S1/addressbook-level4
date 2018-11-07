package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.clubbudget.FinalClubBudget;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyFinalBudgetBook {

    /**
     * Returns an unmodifiable view of the final budgets list.
     * This list will not contain any duplicate final budgets.
     */
    ObservableList<FinalClubBudget> getClubBudgetsList();

}
