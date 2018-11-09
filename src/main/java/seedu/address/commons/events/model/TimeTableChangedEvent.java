package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.TimeTable;

/** Indicates the {@code TimeTable} in {@code Model} has changed*/
public class TimeTableChangedEvent extends BaseEvent {

    public final TimeTable data;

    public TimeTableChangedEvent(TimeTable data) {
        this.data = data;
    }

    public TimeTable getNewTimeTable() {
        return data;
    }

    @Override
    public String toString() {
        return "Number of timeslots: " + data.getTimeSlots().size();
    }
}
