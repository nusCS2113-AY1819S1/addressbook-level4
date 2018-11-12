package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTrackedDataList;

/** Indicates the TrackedDataList in the model has changed*/
public class TrackedDataListChangedEvent extends BaseEvent {

    public final ReadOnlyTrackedDataList data;

    public TrackedDataListChangedEvent(ReadOnlyTrackedDataList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of parameters being tracked " + data.getTrackedDataList().size();
    }
}
