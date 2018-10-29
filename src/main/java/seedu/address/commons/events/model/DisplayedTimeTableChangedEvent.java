package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.TimeTable;

/** Indicates the TimeTable in the model has changed*/
public class DisplayedTimeTableChangedEvent extends BaseEvent {

    public final TimeTable data;

    public DisplayedTimeTableChangedEvent(TimeTable data) {
        this.data = data;
    }

    public TimeTable getNewTimeTable() {
        return data;
    }

    @Override
    public String toString() {
        return "TimeTable";
    }
}
