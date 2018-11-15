package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyDistributorBook;

/** Indicates the AddressBook in the model has changed*/
public class DistributorBookChangedEvent extends BaseEvent {

    public final ReadOnlyDistributorBook data;

    public DistributorBookChangedEvent(ReadOnlyDistributorBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of distributors " + data.getDistributorList().size();
    }
}
