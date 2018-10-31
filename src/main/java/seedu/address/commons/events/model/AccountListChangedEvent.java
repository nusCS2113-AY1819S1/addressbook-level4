package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAccountList;

/** Indicates the AccountList in the model has changed*/
public class AccountListChangedEvent extends BaseEvent {

    public final ReadOnlyAccountList data;

    public AccountListChangedEvent(ReadOnlyAccountList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of accounts " + data.getAccountList().size();
    }
}
