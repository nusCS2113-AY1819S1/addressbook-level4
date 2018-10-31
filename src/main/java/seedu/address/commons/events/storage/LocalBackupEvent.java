//@@author QzSG
package seedu.address.commons.events.storage;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.model.ReadOnlyTaskBook;


/** Indicates a AddressBook restore request*/
public class LocalBackupEvent extends BaseEvent {

    public final ReadOnlyAddressBook readOnlyAddressBook;
    public final ReadOnlyEventBook readOnlyEventBook;
    public final ReadOnlyExpenseBook readOnlyExpenseBook;
    public final ReadOnlyTaskBook readOnlyTaskBook;

    public final Path addressBookPath;
    public final Path eventBookPath;
    public final Path expenseBookPath;
    public final Path taskBookPath;

    public LocalBackupEvent(ReadOnlyAddressBook readOnlyAddressBook, Path addressBookPath,
                            ReadOnlyEventBook readOnlyEventBook, Path eventBookPath,
                            ReadOnlyExpenseBook readOnlyExpenseBook, Path expenseBookPath,
                            ReadOnlyTaskBook readOnlyTaskBook, Path taskBookPath) {
        this.readOnlyAddressBook = readOnlyAddressBook;
        this.readOnlyEventBook = readOnlyEventBook;
        this.readOnlyExpenseBook = readOnlyExpenseBook;
        this.readOnlyTaskBook = readOnlyTaskBook;

        this.addressBookPath = addressBookPath;
        this.eventBookPath = eventBookPath;
        this.expenseBookPath = expenseBookPath;
        this.taskBookPath = taskBookPath;

    }

    @Override
    public String toString() {
        return "Sending local backup request to storage manager";
    }
}
