package seedu.address.commons.events.model;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyEventBook;

/** Indicates the EventBook in the model has changed*/
public class EventBookLocalBackupEvent extends BaseEvent {

    public final ReadOnlyEventBook data;
    public final Path filePath;

    public EventBookLocalBackupEvent(ReadOnlyEventBook data, Path filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Creating local backup at " + filePath.toString();
    }
}
