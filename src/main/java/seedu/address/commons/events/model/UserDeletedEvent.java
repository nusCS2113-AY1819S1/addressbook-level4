package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.login.User;

/** Indicates the User in the model has been removed*/
public class UserDeletedEvent extends BaseEvent {
    public final User data;

    public UserDeletedEvent(User user) {
        this.data = user;
    }

    @Override
    public String toString() {
        return "user deleted: " + data.getUsername().toString();
    }
}
