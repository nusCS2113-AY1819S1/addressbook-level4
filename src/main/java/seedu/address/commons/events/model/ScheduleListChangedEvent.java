package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.schedule.ReadOnlyScheduleList;

/** Indicates the Schedule List in the model has changed*/
public class ScheduleListChangedEvent extends BaseEvent {

    public final ReadOnlyScheduleList data;

    public ScheduleListChangedEvent(ReadOnlyScheduleList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of schedules: " + data.getScheduleList().size();
    }
}
