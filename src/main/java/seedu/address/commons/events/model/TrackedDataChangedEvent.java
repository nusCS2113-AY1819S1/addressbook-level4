package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyTrackedData;

/** Indicates the WorkoutBook in the model has changed*/
public class TrackedDataChangedEvent extends BaseEvent {

    public final ReadOnlyTrackedData data;

    public TrackedDataChangedEvent(ReadOnlyTrackedData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of workouts " + data.getTrackedData().size();
    }
}
