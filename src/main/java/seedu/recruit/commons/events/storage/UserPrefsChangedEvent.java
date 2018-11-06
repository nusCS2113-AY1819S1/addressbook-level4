package seedu.recruit.commons.events.storage;

import seedu.recruit.commons.events.BaseEvent;
import seedu.recruit.model.UserPrefs;

/** Indicates the UserPrefs has changed*/

public class UserPrefsChangedEvent extends BaseEvent {
    public final UserPrefs data;

    public UserPrefsChangedEvent(UserPrefs userPrefs) {
        this.data = userPrefs;
    }

    @Override
    public String toString() {
        return "updated UserPrefs";
    }
}
