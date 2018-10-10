package seedu.address.commons.events.storage;

import java.util.Optional;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.OnlineStorage;

//@@author QzSG
/** Indicates a request for online backup*/
public class OnlineBackupEvent extends BaseEvent {

    public final OnlineStorage.OnlineStorageType target;
    public final ReadOnlyAddressBook data;
    public final String fileName;
    public final Optional<String> authToken;

    public OnlineBackupEvent(OnlineStorage.OnlineStorageType target, ReadOnlyAddressBook data,
                             String fileName, Optional<String> authToken) {
        this.target = target;
        this.data = data;
        this.fileName = fileName;
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "Saving data online";
    }
}
