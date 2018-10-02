package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.workout.Workout;

/**
 * Unmodifiable view of an workout book
 */
public interface ReadOnlyWorkoutBook {

    /**
     * Returns an unmodifiable view of the workouts list.
     * This list will not contain any duplicate workout.
     */
    ObservableList<Workout> getWorkoutList();

}
