package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.clubbudget.FinalClubBudget;

/**
 * Represents a selection change in the Person List Panel
 */
public class BudgetPanelSelectionChangedEvent extends BaseEvent {


    private final FinalClubBudget newSelection;

    public BudgetPanelSelectionChangedEvent(FinalClubBudget newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public FinalClubBudget getNewSelection() {
        return newSelection;
    }
}
