//@@author QzSG
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/** Indicates a AddressBook restore request*/
public class AddressBookLocalRestoreEvent extends BaseEvent {

    public final ReadOnlyAddressBook readOnlyAddressBook;

    public AddressBookLocalRestoreEvent(ReadOnlyAddressBook readOnlyAddressBook) {
        this.readOnlyAddressBook = readOnlyAddressBook;
    }

    @Override
    public String toString() {
        return "Restoring local backup from " + readOnlyAddressBook.toString();
    }
}
