package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.TimeTable;

/** Indicates the TimeTable in the model has changed*/
public class TimeTableChangedEvent extends BaseEvent {

    public final TimeTable data;

    public TimeTableChangedEvent(TimeTable data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TimeTable";
    }
}
