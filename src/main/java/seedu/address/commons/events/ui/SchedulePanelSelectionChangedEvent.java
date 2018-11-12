package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a selection change in the schedule List Panel
 */
public class SchedulePanelSelectionChangedEvent extends BaseEvent {


    private final Schedule newSelection;

    public SchedulePanelSelectionChangedEvent(Schedule newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
