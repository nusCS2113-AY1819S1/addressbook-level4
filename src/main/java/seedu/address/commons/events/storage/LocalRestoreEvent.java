package seedu.address.commons.events.storage;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;

//@@author QzSG

/** Indicates a request for local restore*/
public class LocalRestoreEvent extends BaseEvent {

    public final Path addressBookPath;
    public final Path eventBookPath;
    public final Path expenseBookPath;
    public final Path taskBookPath;

    public LocalRestoreEvent(Path addressBookPath, Path eventBookPath, Path expenseBookPath, Path taskBookPath) {
        this.addressBookPath = addressBookPath;
        this.eventBookPath = eventBookPath;
        this.expenseBookPath = expenseBookPath;
        this.taskBookPath = taskBookPath;
    }

    @Override
    public String toString() {
        return "Restoring local backup";
    }
}
