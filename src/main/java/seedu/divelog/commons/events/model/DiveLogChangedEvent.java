package seedu.divelog.commons.events.model;

import seedu.divelog.commons.events.BaseEvent;
import seedu.divelog.model.ReadOnlyDiveLog;

/** Indicates the DiveLog in the model has changed*/
public class DiveLogChangedEvent extends BaseEvent {

    public final ReadOnlyDiveLog data;

    public DiveLogChangedEvent(ReadOnlyDiveLog data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of dives " + data.getDiveList().size();
    }
}
