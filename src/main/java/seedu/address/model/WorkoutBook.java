package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.workout.UniqueWorkoutList;
import seedu.address.model.workout.Workout;

/**
 * Wraps all data at the workout-book level
 * Duplicates are not allowed (by .isSameWorkout comparison)
 */
public class WorkoutBook implements ReadOnlyWorkoutBook {

    private final UniqueWorkoutList workouts;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        workouts = new UniqueWorkoutList();
    }

    public WorkoutBook() {}

    /**
     * Creates an WorkoutBook using the Workouts in the {@code toBeCopied}
     */
    public WorkoutBook(ReadOnlyWorkoutBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Workout list with {@code workouts}.
     * {@code workouts} must not contain duplicate workouts.
     */
    public void setWorkouts(List<Workout> workouts) {
        this.workouts.setWorkouts(workouts);
    }

    public List<Workout> getFinalFilteredInternalList (RecommendArguments recommendArguments) {
        return workouts.getFinalFilteredInternalList(recommendArguments);
    }

    /**
     * Resets the existing data of this {@code WorkoutBook} with {@code newData}.
     */
    public void resetData(ReadOnlyWorkoutBook newData) {
        requireNonNull(newData);

        setWorkouts(newData.getWorkoutList());
    }

    //// workout-level operations

    /**
     * Returns true if a workout with the same identity as {@code workout} exists in the workout book.
     */
    public boolean hasWorkout(Workout workout) {
        requireNonNull(workout);
        return workouts.contains(workout);
    }

    /**
     * Adds a workout to the workout book.
     * The workout must not already exist in the workout book.
     */
    public void addWorkout(Workout w) {
        workouts.add(w);
    }

    /**
     * Replaces the given workout {@code target} in the list with {@code editedWorkout}.
     * {@code target} must exist in the workout book.
     * The workout identity of {@code editedWorkout} must not be the same as another existing workout in the workout
     * book.
     */
    public void updateWorkout(Workout target, Workout editedWorkout) {
        requireNonNull(editedWorkout);
        workouts.setWorkout(target, editedWorkout);
    }

    public void sortFilteredWorkoutList() {
        workouts.sort();
    }

    /**
     * Removes {@code key} from this {@code WorkoutBook}.
     * {@code key} must exist in the workout book.
     */
    public void removeWorkout(Workout key) {
        workouts.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return workouts.asUnmodifiableObservableList().size() + " workouts";
    }

    @Override
    public ObservableList<Workout> getWorkoutList() {
        return workouts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkoutBook // instanceof handles nulls
                && workouts.equals(((WorkoutBook) other).workouts));
    }

    @Override
    public int hashCode() {
        return workouts.hashCode();
    }
}
