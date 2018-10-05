package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyUserDatabase;

public class UserDatabaseChangedEvent extends BaseEvent {

    public final ReadOnlyUserDatabase data;

    public UserDatabaseChangedEvent(ReadOnlyUserDatabase data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of users: " + data.getUsersList().size();
    }
}
