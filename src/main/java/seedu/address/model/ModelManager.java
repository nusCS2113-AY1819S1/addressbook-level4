package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.WorkoutBookChangedEvent;
import seedu.address.model.workout.Workout;

/**
 * Represents the in-memory model of the workout book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedWorkoutBook versionedWorkoutBook;
    private final FilteredList<Workout> filteredWorkouts;

    /**
     * Initializes a ModelManager with the given workoutBook and userPrefs.
     */
    public ModelManager(ReadOnlyWorkoutBook workoutBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(workoutBook, userPrefs);

        logger.fine("Initializing with workout book: " + workoutBook + " and user prefs " + userPrefs);

        versionedWorkoutBook = new VersionedWorkoutBook(workoutBook);
        filteredWorkouts = new FilteredList<>(versionedWorkoutBook.getWorkoutList());
    }

    public ModelManager() {
        this(new WorkoutBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyWorkoutBook newData) {
        versionedWorkoutBook.resetData(newData);
        indicateWorkoutBookChanged();
    }

    @Override
    public ReadOnlyWorkoutBook getWorkoutBook() {
        return versionedWorkoutBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateWorkoutBookChanged() {
        raise(new WorkoutBookChangedEvent(versionedWorkoutBook));
    }

    @Override
    public boolean hasWorkout(Workout workout) {
        requireNonNull(workout);
        return versionedWorkoutBook.hasWorkout(workout);
    }

    @Override
    public void deleteWorkout(Workout target) {
        versionedWorkoutBook.removeWorkout(target);
        indicateWorkoutBookChanged();
    }

    @Override
    public void addWorkout(Workout workout) {
        versionedWorkoutBook.addWorkout(workout);
        updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);
        indicateWorkoutBookChanged();
    }

    @Override
    public void updateWorkout(Workout target, Workout editedWorkout) {
        requireAllNonNull(target, editedWorkout);

        versionedWorkoutBook.updateWorkout(target, editedWorkout);
        indicateWorkoutBookChanged();
    }

    //=========== Filtered Workout List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Workout} backed by the internal list of
     * {@code versionedWorkoutBook}
     */
    @Override
    public ObservableList<Workout> getFilteredWorkoutList() {
        return FXCollections.unmodifiableObservableList(filteredWorkouts);
    }

    @Override
    public void updateFilteredWorkoutList(Predicate<Workout> predicate) {
        requireNonNull(predicate);
        filteredWorkouts.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoWorkoutBook() {
        return versionedWorkoutBook.canUndo();
    }

    @Override
    public boolean canRedoWorkoutBook() {
        return versionedWorkoutBook.canRedo();
    }

    @Override
    public void undoWorkoutBook() {
        versionedWorkoutBook.undo();
        indicateWorkoutBookChanged();
    }

    @Override
    public void redoWorkoutBook() {
        versionedWorkoutBook.redo();
        indicateWorkoutBookChanged();
    }

    @Override
    public void commitWorkoutBook() {
        versionedWorkoutBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedWorkoutBook.equals(other.versionedWorkoutBook)
                && filteredWorkouts.equals(other.filteredWorkouts);
    }

}
