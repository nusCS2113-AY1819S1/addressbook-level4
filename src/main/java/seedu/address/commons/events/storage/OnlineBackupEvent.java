package seedu.address.commons.events.storage;

import java.util.Optional;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.storage.OnlineStorage;

//@@author QzSG
/** Indicates a request for online backup*/
public class OnlineBackupEvent extends BaseEvent {

    public final OnlineStorage.Type target;
    public final ReadOnlyAddressBook addressData;
    public final ReadOnlyEventBook eventData;
    public final ReadOnlyExpenseBook expenseData;
    public final ReadOnlyTaskBook taskData;
    public final Optional<String> authToken;

    public OnlineBackupEvent(OnlineStorage.Type target, ReadOnlyAddressBook addressData, ReadOnlyEventBook eventData,
                             ReadOnlyExpenseBook expenseData, ReadOnlyTaskBook taskData , Optional<String> authToken) {
        this.target = target;
        this.addressData = addressData;
        this.eventData = eventData;
        this.expenseData = expenseData;
        this.taskData = taskData;
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "Saving data online";
    }
}
