//@@author QzSG
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyExpenseBook;

/** Indicates a AddressBook restore request*/
public class ExpenseBookLocalRestoreEvent extends BaseEvent {

    public final ReadOnlyExpenseBook readOnlyExpenseBook;

    public ExpenseBookLocalRestoreEvent(ReadOnlyExpenseBook readOnlyExpenseBook) {
        this.readOnlyExpenseBook = readOnlyExpenseBook;
    }

    @Override
    public String toString() {
        return "Restoring local backup from " + readOnlyExpenseBook.toString();
    }
}
