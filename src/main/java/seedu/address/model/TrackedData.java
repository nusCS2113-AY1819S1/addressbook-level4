package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.workout.Workout;

/**
 * Analogous to WorkoutBook; wraps all data at the workout-book level, for each tracked parameter
 * Duplicates are allowed.
 */
public class TrackedData implements ReadOnlyTrackedData {

    private final WorkoutList workouts;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        workouts = new WorkoutList();
    }

    public TrackedData() {}

    /**
     * Creates an TrackedData using the Workouts in the {@code toBeCopied}
     */
    public TrackedData(ReadOnlyTrackedData toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the TrackedData with {@code workouts}.
     * {@code workouts} must not contain duplicate workouts.
     */
    public void setWorkouts(List<Workout> workouts) {
        this.workouts.setWorkouts(workouts);
    }

    /**
     * Resets the existing data of this {@code TrackedData} with {@code newData}.
     */
    public void resetData(ReadOnlyTrackedData newData) {
        requireNonNull(newData);

        setWorkouts(newData.getTrackedData());
    }

    //// workout-level operations

    /**
     * Returns true if a workout with the same identity as {@code workout} exists in the tracked data.
     */
    public boolean hasWorkout(Workout workout) {
        requireNonNull(workout);
        return workouts.contains(workout);
    }

    /**
     * Adds a workout to the TrackedData.
     * The workout must not already exist in the TrackedData.
     */
    public void addWorkout(Workout w) {
        workouts.add(w);
    }

    public void sortFilteredWorkoutList() {
        workouts.sort();
    }

    //// util methods

    @Override
    public String toString() {
        return workouts.asUnmodifiableObservableList().size() + " workouts";
    }

    @Override
    public ObservableList<Workout> getTrackedData() {
        return workouts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TrackedData // instanceof handles nulls
                && workouts.equals(((TrackedData) other).workouts));
    }

    @Override
    public int hashCode() {
        return workouts.hashCode();
    }

    /**
     * A list of workouts that allows for duplicates between its elements, and does not allow nulls.
     *
     * Supports a minimal set of list operations.
     */
    public class WorkoutList implements Iterable<Workout> {

        private final ObservableList<Workout> internalList = FXCollections.observableArrayList();

        /**
         * Returns true if the list contains an equivalent workout as the given argument.
         */
        public boolean contains(Workout toCheck) {
            requireNonNull(toCheck);
            return internalList.stream().anyMatch(toCheck::isSameWorkout);
        }

        /**
         * Adds a workout to tracked data.
         */
        public void add(Workout toAdd) {
            requireNonNull(toAdd);
            internalList.add(toAdd);
        }

        /**
         * Sorts the workout in the list
         */
        public void sort() {
            internalList.sort(Comparator.comparing(o -> o.getName().fullName));
        }

        public void setWorkouts(WorkoutList replacement) {
            requireNonNull(replacement);
            internalList.setAll(replacement.internalList);
        }

        /**
         * Replaces the contents of this list with {@code workouts}.
         */
        public void setWorkouts(List<Workout> workouts) {
            requireAllNonNull(workouts);
            internalList.setAll(workouts);
        }

        /**
         * Returns the backing list as an unmodifiable {@code ObservableList}.
         */
        public ObservableList<Workout> asUnmodifiableObservableList() {
            return FXCollections.unmodifiableObservableList(internalList);
        }

        @Override
        public Iterator<Workout> iterator() {
            return internalList.iterator();
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof WorkoutList // instanceof handles nulls
                    && internalList.equals(((WorkoutList) other).internalList));
        }

        @Override
        public int hashCode() {
            return internalList.hashCode();
        }
    }
}
