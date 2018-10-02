package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyWorkoutBook;

/** Indicates the WorkoutBook in the model has changed*/
public class WorkoutBookChangedEvent extends BaseEvent {

    public final ReadOnlyWorkoutBook data;

    public WorkoutBookChangedEvent(ReadOnlyWorkoutBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of workouts " + data.getWorkoutList().size();
    }
}
