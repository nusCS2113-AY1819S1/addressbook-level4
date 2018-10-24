package seedu.address.commons.events.model;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyExpenseBook;

/** Indicates the ExpenseBook in the model has changed*/
public class ExpenseBookLocalBackupEvent extends BaseEvent {

    public final ReadOnlyExpenseBook data;
    public final Path filePath;

    public ExpenseBookLocalBackupEvent(ReadOnlyExpenseBook data, Path filePath) {
        this.data = data;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Creating local backup at " + filePath.toString();
    }
}
