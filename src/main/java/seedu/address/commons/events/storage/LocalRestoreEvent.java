package seedu.address.commons.events.storage;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;

//@@author QzSG

/** Indicates a request for local restore*/
public class LocalRestoreEvent extends BaseEvent {

    public final Path addressBookPath;
    public final Path expenseBookPath;

    public LocalRestoreEvent(Path addressBookPath, Path expenseBookPath) {
        this.addressBookPath = addressBookPath;
        this.expenseBookPath = expenseBookPath;
    }

    @Override
    public String toString() {
        return "Restoring local backup";
    }
}
