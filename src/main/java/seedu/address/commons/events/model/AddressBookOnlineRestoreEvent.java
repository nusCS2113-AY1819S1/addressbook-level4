//@@author QzSG
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/** Indicates a AddressBook online restore request*/
public class AddressBookOnlineRestoreEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public AddressBookOnlineRestoreEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }
    @Override
    public String toString() {
        return "Restoring online backup";
    }
}
