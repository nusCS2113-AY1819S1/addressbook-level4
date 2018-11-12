package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.expenses.Expenses;

/**
 * Represents a selection change in the Expenses List Panel
 */
public class ExpensesPanelSelectionChangedEvent extends BaseEvent {

    private final Expenses newSelection;

    public ExpensesPanelSelectionChangedEvent(Expenses newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
