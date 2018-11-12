package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.request.ReadOnlyRequests;

/** Indicates the BookInventory in the model has changed*/
public class RequestListChangedEvent extends BaseEvent {

    public final ReadOnlyRequests dataRequest;

    public RequestListChangedEvent(ReadOnlyRequests dataRequest) {
        this.dataRequest = dataRequest;
    }

    @Override
    public String toString() {
        return "number of requests " + dataRequest.getRequestList().size();
    }
}
