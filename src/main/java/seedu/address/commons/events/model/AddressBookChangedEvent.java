package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyCandidateBook;

/** Indicates the CandidateBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyCandidateBook data;

    public AddressBookChangedEvent(ReadOnlyCandidateBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getCandidatelist().size();
    }
}
