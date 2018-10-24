package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.UserPrefs;
import seedu.address.storage.OnlineStorage;

//@@author QzSG

/** Indicates a request for online backup*/
public class OnlineBackupSuccessResultEvent extends BaseEvent {

    public final OnlineStorage.Type target;
    public final UserPrefs.TargetBook targetBook;
    public final String ref;

    public OnlineBackupSuccessResultEvent(OnlineStorage.Type target, UserPrefs.TargetBook targetBook, String ref) {
        this.target = target;
        this.targetBook = targetBook;
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "Signaling user preference update with success reference from specific online service";
    }
}
