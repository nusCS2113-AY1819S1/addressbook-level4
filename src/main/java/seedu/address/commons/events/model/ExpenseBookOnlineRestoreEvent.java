//@@author QzSG
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyExpenseBook;

/** Indicates a ExpenseBook online restore request*/
public class ExpenseBookOnlineRestoreEvent extends BaseEvent {

    public final ReadOnlyExpenseBook data;

    public ExpenseBookOnlineRestoreEvent(ReadOnlyExpenseBook data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Restoring online backup";
    }
}
