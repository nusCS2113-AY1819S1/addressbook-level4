package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyLoginBook;

/** Indicates the LoginBook in the model has changed*/
public class LoginBookChangedEvent extends BaseEvent {

    public final ReadOnlyLoginBook data;

    public LoginBookChangedEvent(ReadOnlyLoginBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of accounts " + data.getLoginDetailsList().size();
    }
}
