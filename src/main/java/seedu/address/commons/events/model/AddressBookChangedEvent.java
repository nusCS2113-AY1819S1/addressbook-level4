package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public AddressBookChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    /*
    public String toDString() {
        return "number of distributors " + data.getDistributorList().size();
    }
    */

    @Override
    public String toString() {
        return "number of products " + data.getPersonList().size();
    }
}
