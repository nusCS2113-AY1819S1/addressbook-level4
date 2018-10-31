package seedu.address.commons.events.model;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTaskBook;


/** Indicates the ExpenseBook in the model has changed*/
public class TaskBookLocalBackupEvent extends BaseEvent {

    public final ReadOnlyTaskBook data;
    public final Path filePath;

    public TaskBookLocalBackupEvent(ReadOnlyTaskBook data, Path filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Creating local backup at " + filePath.toString();
    }
}
