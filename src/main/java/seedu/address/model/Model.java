package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.workout.Workout;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Workout> PREDICATE_SHOW_ALL_WORKOUTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyWorkoutBook newData);

    /** Returns the WorkoutBook */
    ReadOnlyWorkoutBook getWorkoutBook();

    /**
     * Returns true if a workout with the same identity as {@code workout} exists in the workout book.
     */
    boolean hasWorkout(Workout workout);

    /**
     * Deletes the given workout.
     * The workout must exist in the workout book.
     */
    void deleteWorkout(Workout target);

    /**
     * Adds the given workout.
     * {@code workout} must not already exist in the workout book.
     */
    void addWorkout(Workout workout);

    /**
     * Replaces the given workout {@code target} with {@code editedWorkout}.
     * {@code target} must exist in the workout book.
     * The workout identity of {@code editedWorkout} must not be the same as another existing workout in the workout book.
     */
    void updateWorkout(Workout target, Workout editedWorkout);

    /** Returns an unmodifiable view of the filtered workout list */
    ObservableList<Workout> getFilteredWorkoutList();

    /**
     * Updates the filter of the filtered workout list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWorkoutList(Predicate<Workout> predicate);

    /**
     * Returns true if the model has previous workout book states to restore.
     */
    boolean canUndoWorkoutBook();

    /**
     * Returns true if the model has undone workout book states to restore.
     */
    boolean canRedoWorkoutBook();

    /**
     * Restores the model's workout book to its previous state.
     */
    void undoWorkoutBook();

    /**
     * Restores the model's workout book to its previously undone state.
     */
    void redoWorkoutBook();

    /**
     * Saves the current workout book state for undo/redo.
     */
    void commitWorkoutBook();
}
