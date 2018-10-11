package seedu.address.model.request;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookInventory;

/** Indicates the BookInventory in the model has changed*/
public class RequestListChangedEvent extends BaseEvent {

    public final ReadOnlyRequests data;

    public RequestListChangedEvent(ReadOnlyRequests data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getRequestList().size();
    }
}
