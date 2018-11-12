package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.workout.Parameter;

/**
 * Represents a selection change in the Workout List Panel
 */
public class TrackedDataPanelSelectionChangedEvent extends BaseEvent {


    private final Parameter newSelection;

    public TrackedDataPanelSelectionChangedEvent(Parameter newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Parameter getNewSelection() {
        return newSelection;
    }
}
