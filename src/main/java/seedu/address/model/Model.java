package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.Workout;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Workout> PREDICATE_SHOW_ALL_WORKOUTS = unused -> true;

    /** {@code Predicate} that always evaluate to false */
    Predicate<Workout> PREDICATE_SHOW_NO_WORKOUTS = unused -> false;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Parameter> PREDICATE_SHOW_ALL_PARAMETERS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyWorkoutBook newData);

    /** Returns the WorkoutBook */
    ReadOnlyWorkoutBook getWorkoutBook();

    /** Returns the TrackedDataList */
    ReadOnlyTrackedDataList getTrackedDataList();

    /** Returns the TrackedData */
    ReadOnlyTrackedData getTrackedData();

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
     * The workout identity of {@code editedWorkout} must not be the same as another existing workout in the workout
     * book.
     */
    void updateWorkout(Workout target, Workout editedWorkout);

    /** Returns an unmodifiable view of the filtered workout list */
    ObservableList<Workout> getFilteredWorkoutList();

    /**
     * Updates the filter of the filtered workout list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredWorkoutList(Predicate<Workout> predicate);

    /** Returns an unmodifiable view of the filtered tracked data list */
    ObservableList<Parameter> getFilteredTrackedDataList();

    /**
     * Updates the filter of the filtered tracked data list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTrackedDataList(Predicate<Parameter> predicate);

    /** Returns an unmodifiable view of the filtered tracked data */
    ObservableList<Workout> getFilteredTrackedData();

    /**
     * Updates the filter of the filtered tracked data to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTrackedData(Predicate<Workout> predicate);

    /**
     * Returns true if the model has previous states to restore.
     */
    boolean canUndoModel();

    /**
     * Returns true if the model has undone states to restore.
     */
    boolean canRedoModel();

    /**
     * Restores the model to its previous state.
     */
    void undoModel();

    /**
     * Restores the model to its previously undone state.
     */
    void redoModel();

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
     * Returns true if the model has previous tracked data list states to restore.
     */
    boolean canUndoTrackedDataList();

    /**
     * Returns true if the model has undone tracked data list states to restore.
     */
    boolean canRedoTrackedDataList();

    /**
     * Restores the model's tracked data list to its previous state.
     */
    void undoTrackedDataList();

    /**
     * Restores the model's tracked data list to its previously undone state.
     */
    void redoTrackedDataList();

    /**
     * Returns true if the model has previous tracked data states to restore.
     */
    boolean canUndoTrackedData();

    /**
     * Returns true if the model has undone tracked data states to restore.
     */
    boolean canRedoTrackedData();

    /**
     * Restores the model's tracked data to its previous state.
     */
    void undoTrackedData();

    /**
     * Restores the model's tracked data to its previously undone state.
     */
    void redoTrackedData();

    /**
     * Saves the current model state for undo/redo.
     */
    void commitModel();

    /**
     * Saves the current workout book state for undo/redo.
     */
    void commitWorkoutBook();

    /**
     * Saves the current tracked data list state for undo/redo.
     */
    void commitTrackedDataList();

    /**
     * Saves the current tracked data  state for undo/redo.
     */
    void commitTrackedData();

    /**
     * Sort the current workout book.
     */
    void sortFilteredWorkoutList();

    /**
     * Adds the given parameter to the tracked data list.
     * The parameter must not already exist in the tracked data list.
     */
    void addDataToTrack(Parameter parameter);

    /**
     * Deletes the given parameter.
     * The parameter must exist in the tracked data list.
     */
    void removeDataFromTrack(Parameter parameter);

    /**
     * Check whether the completed workout should be tracked.
     */
    void checkDataForTrack(Workout workout);

    /**
     * Returns true if {@code parameter} exists in the tracked data list.
     */
    boolean hasParameter(Parameter parameter);

    /**
     * Returns the filtered internal list.
     */
    List<Workout> getFinalFilteredInternalList(RecommendArguments recommendArguments);
}
