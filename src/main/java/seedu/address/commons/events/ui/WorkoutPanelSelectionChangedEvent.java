package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.workout.Workout;

/**
 * Represents a selection change in the Workout List Panel
 */
public class WorkoutPanelSelectionChangedEvent extends BaseEvent {


    private final Workout newSelection;

    public WorkoutPanelSelectionChangedEvent(Workout newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Workout getNewSelection() {
        return newSelection;
    }
}
