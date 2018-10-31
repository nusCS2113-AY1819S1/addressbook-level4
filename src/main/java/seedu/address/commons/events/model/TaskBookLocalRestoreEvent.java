//@@author QzSG
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskBook;

/** Indicates a AddressBook restore request*/
public class TaskBookLocalRestoreEvent extends BaseEvent {

    public final ReadOnlyTaskBook readOnlyTaskBook;

    public TaskBookLocalRestoreEvent(ReadOnlyTaskBook readOnlyTaskBook) {
        this.readOnlyTaskBook = readOnlyTaskBook;
    }

    @Override
    public String toString() {
        return "Restoring local backup from " + readOnlyTaskBook.toString();
    }
}
