package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EQUIPMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TrackedDataChangedEvent;
import seedu.address.commons.events.model.TrackedDataListChangedEvent;
import seedu.address.commons.events.model.WorkoutBookChangedEvent;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Instruction;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.Remark;
import seedu.address.model.workout.Type;
import seedu.address.model.workout.Workout;

/**
 * Represents the in-memory model of the workout book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedWorkoutBook versionedWorkoutBook;
    private final VersionedTrackedDataList versionedTrackedDataList;
    private final VersionedTrackedData versionedTrackedData;
    private final FilteredList<Workout> filteredWorkouts;
    private final FilteredList<Parameter> filteredParameters;
    private final FilteredList<Workout> filteredTrackedData;

    /**
     * Initializes a ModelManager with the given workoutBook and userPrefs.
     */
    public ModelManager(ReadOnlyWorkoutBook workoutBook, ReadOnlyTrackedDataList trackedDataList,
                        ReadOnlyTrackedData trackedData, UserPrefs userPrefs) {
        super();
        requireAllNonNull(workoutBook, userPrefs);

        logger.fine("Initializing with workout book: " + workoutBook + " and user prefs " + userPrefs);

        versionedWorkoutBook = new VersionedWorkoutBook(workoutBook);
        filteredWorkouts = new FilteredList<>(versionedWorkoutBook.getWorkoutList());
        versionedTrackedDataList = new VersionedTrackedDataList(trackedDataList);
        filteredParameters = new FilteredList<>(versionedTrackedDataList.getTrackedDataList());
        versionedTrackedData = new VersionedTrackedData(trackedData);
        filteredTrackedData = new FilteredList<>(versionedTrackedData.getTrackedData());
        updateFilteredTrackedData(PREDICATE_SHOW_NO_WORKOUTS);
    }

    public ModelManager() {
        this(new WorkoutBook(), new TrackedDataList(), new TrackedData(), new UserPrefs());
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

    @Override
    public ReadOnlyTrackedDataList getTrackedDataList() {
        return versionedTrackedDataList;
    }

    @Override
    public ReadOnlyTrackedData getTrackedData() {
        return versionedTrackedData;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateWorkoutBookChanged() {
        raise(new WorkoutBookChangedEvent(versionedWorkoutBook));
    }

    private void indicateTrackedDataChanged() {
        raise(new TrackedDataChangedEvent(versionedTrackedData));
    }

    private void indicateTrackedDataListChanged() {
        raise(new TrackedDataListChangedEvent(versionedTrackedDataList));
    }

    @Override
    public boolean hasWorkout(Workout workout) {
        requireNonNull(workout);
        return versionedWorkoutBook.hasWorkout(workout);
    }

    @Override
    public void deleteWorkout(Workout target) {
        versionedWorkoutBook.removeWorkout(target);
        updateFilteredTrackedData(PREDICATE_SHOW_NO_WORKOUTS);
        indicateWorkoutBookChanged();
    }

    @Override
    public void addWorkout(Workout workout) {
        versionedWorkoutBook.addWorkout(workout);
        updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);
        updateFilteredTrackedData(PREDICATE_SHOW_NO_WORKOUTS);
        indicateWorkoutBookChanged();
    }

    @Override
    public void addDataToTrack(Parameter parameter) {
        versionedTrackedDataList.addParameter(parameter);
        updateFilteredTrackedDataList(PREDICATE_SHOW_ALL_PARAMETERS);
        updateFilteredTrackedData(PREDICATE_SHOW_NO_WORKOUTS);
        indicateTrackedDataListChanged();
    }

    @Override
    public void removeDataFromTrack(Parameter parameter) {
        versionedTrackedDataList.removeParameter(parameter);
        updateFilteredTrackedData(PREDICATE_SHOW_NO_WORKOUTS);
        indicateTrackedDataListChanged();
    }

    @Override
    public void checkDataForTrack(Workout workout) {
        boolean hasParameter = false;
        for (Parameter p : filteredParameters) {
            Prefix prefix = p.getPrefix();
            String value = p.getValue();
            if (prefix.equals(PREFIX_NAME)) {
                Name name = workout.getName();
                if (name.fullName.equals(value)) {
                    hasParameter = true;
                }
            } else if (prefix.equals(PREFIX_TYPE)) {
                Type type = workout.getType();
                if (type.fullType.equals(value)) {
                    hasParameter = true;
                }
            } else if (prefix.equals(PREFIX_DURATION)) {
                Duration duration = workout.getDuration();
                if (duration.fullDuration.equals(value)) {
                    hasParameter = true;
                }
            } else if (prefix.equals(PREFIX_DIFFICULTY)) {
                Difficulty difficulty = workout.getDifficulty();
                if (difficulty.fullDifficulty.equals(value)) {
                    hasParameter = true;
                }
            } else if (prefix.equals(PREFIX_EQUIPMENT)) {
                Equipment equipment = workout.getEquipment();
                if (equipment.fullEquipment.equals(value)) {
                    hasParameter = true;
                }
            } else if (prefix.equals(PREFIX_MUSCLE)) {
                Muscle muscle = workout.getMuscle();
                if (muscle.fullMuscle.equals(value)) {
                    hasParameter = true;
                }
            } else if (prefix.equals(PREFIX_CALORIES)) {
                Calories calories = workout.getCalories();
                if (calories.fullCalories.equals(value)) {
                    hasParameter = true;
                }
            } else if (prefix.equals(PREFIX_INSTRUCTION)) {
                Instruction instruction = workout.getInstruction();
                if (instruction.fullInstruction.equals(value)) {
                    hasParameter = true;
                }
            } else if (prefix.equals(PREFIX_TAG)) {
                Set<Tag> tagSet = workout.getTags();
                for (Tag tag : tagSet) {
                    if (tag.tagName.equals(value)) {
                        hasParameter = true;
                    }
                }
            } else if (prefix.equals(PREFIX_REMARK)) {
                Remark remark = workout.getRemark();
                if (remark.fullRemark.equals(value)) {
                    hasParameter = true;
                }
            }
        }
        if (hasParameter) {
            versionedTrackedData.addWorkout(workout);
        }
        updateFilteredTrackedData(PREDICATE_SHOW_NO_WORKOUTS);
        indicateTrackedDataChanged();
    }

    @Override
    public boolean hasParameter(Parameter parameter) {
        requireNonNull(parameter);
        return versionedTrackedDataList.hasParameter(parameter);
    }

    @Override
    public void sortFilteredWorkoutList() {
        versionedWorkoutBook.sortFilteredWorkoutList();
        updateFilteredTrackedData(PREDICATE_SHOW_NO_WORKOUTS);
        indicateWorkoutBookChanged();
    }

    @Override
    public List<Workout> getFinalFilteredInternalList(RecommendArguments recommendArguments) {
        return versionedWorkoutBook.getFinalFilteredInternalList(recommendArguments);
    }

    @Override
    public void updateWorkout(Workout target, Workout editedWorkout) {
        requireAllNonNull(target, editedWorkout);

        versionedWorkoutBook.updateWorkout(target, editedWorkout);
        updateFilteredTrackedData(PREDICATE_SHOW_NO_WORKOUTS);
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

    //=========== Filtered Tracked Data List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Parameter} backed by the internal list of
     * {@code versionedTrackedDataList}
     */
    @Override
    public ObservableList<Parameter> getFilteredTrackedDataList() {
        return FXCollections.unmodifiableObservableList(filteredParameters);
    }

    @Override
    public void updateFilteredTrackedDataList(Predicate<Parameter> predicate) {
        requireNonNull(predicate);
        filteredParameters.setPredicate(predicate);
    }

    //=========== Filtered Tracked Data Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Workout} backed by the internal list of
     * {@code versionedTrackedData}
     */
    @Override
    public ObservableList<Workout> getFilteredTrackedData() {
        return FXCollections.unmodifiableObservableList(filteredTrackedData);
    }

    @Override
    public void updateFilteredTrackedData(Predicate<Workout> predicate) {
        requireNonNull(predicate);
        filteredTrackedData.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoModel() {
        return canUndoWorkoutBook()
                && canUndoTrackedDataList()
                && canUndoTrackedData();
    }

    @Override
    public boolean canRedoModel() {
        return canRedoWorkoutBook()
                && canRedoTrackedDataList()
                && canRedoTrackedData();
    }

    @Override
    public void undoModel() {
        updateFilteredTrackedData(PREDICATE_SHOW_NO_WORKOUTS);
        undoWorkoutBook();
        undoTrackedDataList();
        undoTrackedData();
    }

    @Override
    public void redoModel() {
        updateFilteredTrackedData(PREDICATE_SHOW_NO_WORKOUTS);
        redoWorkoutBook();
        redoTrackedDataList();
        redoTrackedData();
    }

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
    public boolean canUndoTrackedDataList() {
        return versionedTrackedDataList.canUndo();
    }

    @Override
    public boolean canRedoTrackedDataList() {
        return versionedTrackedDataList.canRedo();
    }

    @Override
    public void undoTrackedDataList() {
        versionedTrackedDataList.undo();
        indicateTrackedDataListChanged();
    }

    @Override
    public void redoTrackedDataList() {
        versionedTrackedDataList.redo();
        indicateTrackedDataListChanged();
    }

    @Override
    public boolean canUndoTrackedData() {
        return versionedTrackedData.canUndo();
    }

    @Override
    public boolean canRedoTrackedData() {
        return versionedTrackedData.canRedo();
    }

    @Override
    public void undoTrackedData() {
        versionedTrackedData.undo();
        indicateTrackedDataChanged();
    }

    @Override
    public void redoTrackedData() {
        versionedTrackedData.redo();
        indicateTrackedDataChanged();
    }

    @Override
    public void commitModel() {
        commitWorkoutBook();
        commitTrackedDataList();
        commitTrackedData();
    }

    @Override
    public void commitWorkoutBook() {
        versionedWorkoutBook.commit();
    }

    @Override
    public void commitTrackedDataList() {
        versionedTrackedDataList.commit();
    }

    @Override
    public void commitTrackedData() {
        versionedTrackedData.commit();
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
                && filteredWorkouts.equals(other.filteredWorkouts)
                && versionedTrackedDataList.equals(other.versionedTrackedDataList)
                && filteredParameters.equals(other.filteredParameters)
                && versionedTrackedData.equals(other.versionedTrackedData)
                && filteredTrackedData.equals(other.filteredTrackedData);
    }

}
