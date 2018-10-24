package seedu.address.commons.events.storage;

import java.util.Optional;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.storage.OnlineStorage;

//@@author QzSG
/** Indicates a request for online backup*/
public class OnlineBackupEvent extends BaseEvent {

    public final OnlineStorage.Type target;
    public final ReadOnlyAddressBook addressData;
    public final ReadOnlyExpenseBook expenseData;
    public final Optional<String> authToken;

    public OnlineBackupEvent(OnlineStorage.Type target, ReadOnlyAddressBook addressData,
                             ReadOnlyExpenseBook expenseData, Optional<String> authToken) {
        this.target = target;
        this.addressData = addressData;
        this.expenseData = expenseData;
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "Saving data online";
    }
}
