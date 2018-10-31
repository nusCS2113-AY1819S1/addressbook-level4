//@@author QzSG
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyEventBook;

/** Indicates a AddressBook restore request*/
public class EventBookLocalRestoreEvent extends BaseEvent {

    public final ReadOnlyEventBook readOnlyEventBook;

    public EventBookLocalRestoreEvent(ReadOnlyEventBook readOnlyEventBook) {
        this.readOnlyEventBook = readOnlyEventBook;
    }

    @Override
    public String toString() {
        return "Restoring local backup from " + readOnlyEventBook.toString();
    }
}
