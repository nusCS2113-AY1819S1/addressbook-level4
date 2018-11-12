package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.workout.Workout;

/**
 * Unmodifiable view of a Tracked data workoutbook
 */
public interface ReadOnlyTrackedData {

    /**
     * Returns an unmodifiable view of a tracked data workoutbook.
     * This list will can contain duplicate workouts.
     */
    ObservableList<Workout> getTrackedData();

}
