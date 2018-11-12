package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WORKOUTS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Instruction;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Type;
import seedu.address.model.workout.Workout;

/**
 * Changes a workout to be a completed workout in the workout book.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a workout to be a completed workout identified "
            + "by the index number used in the displayed workout list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_COMPLETE_WORKOUT_SUCCESS = "Completed Workout: %1$s";
    public static final String MESSAGE_DUPLICATE_COMPLETE_WORKOUT = "This workout is already completed.";
    public static final String MESSAGE_SKIPPED_COMPLETE_WORKOUT = "This workout must already be in the current state.";

    private final Index targetIndex;

    /**
     * @param targetIndex of the person in the filtered workout list to edit the state tag
     */
    public CompleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Workout> filteredWorkoutList = model.getFilteredWorkoutList();
        try {
            Workout workoutToEdit = filteredWorkoutList.get(targetIndex.getZeroBased());
            Workout editedWorkout = createEditedWorkout(workoutToEdit);
            model.updateWorkout(workoutToEdit, editedWorkout);
            model.updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);
            CurrentCommand.setCurrentWorkout(false);
            model.checkDataForTrack(editedWorkout);
            model.commitModel();
            return new CommandResult(String.format(MESSAGE_COMPLETE_WORKOUT_SUCCESS, editedWorkout));
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
        }
    }

    /**
     * Creates and returns a {@code Workout} with the details of {@code workoutToEdit}
     * edited with {@code editWorkoutDescriptor}.
     */
    public static Workout createEditedWorkout(Workout workoutToEdit) throws CommandException {
        assert workoutToEdit != null;

        Name updatedName = workoutToEdit.getName();
        Type updatedType = workoutToEdit.getType();
        Duration updatedDuration = workoutToEdit.getDuration();
        Difficulty updatedDifficulty = workoutToEdit.getDifficulty();
        Equipment updatedEquipment = workoutToEdit.getEquipment();
        Muscle updatedMuscle = workoutToEdit.getMuscle();
        Calories updatedCalories = workoutToEdit.getCalories();
        Instruction updatedInstruction = workoutToEdit.getInstruction();
        Set<Tag> originalTags = workoutToEdit.getTags();
        Set<Tag> updatedTags = new HashSet<>();
        for (Tag entry: originalTags) {
            updatedTags.add(entry);
        }
        Tag future = new Tag("future");
        Tag current = new Tag("current");
        Tag completed = new Tag("completed");

        if (originalTags.contains(completed)) {
            throw new CommandException(MESSAGE_DUPLICATE_COMPLETE_WORKOUT);
        }
        if (originalTags.contains(future)) {
            throw new CommandException(MESSAGE_SKIPPED_COMPLETE_WORKOUT);
        }
        if (originalTags.contains(current)) {
            updatedTags.remove(current);
        }
        updatedTags.add(completed);

        return new Workout(updatedName, updatedType, updatedDuration, updatedDifficulty, updatedEquipment,
                updatedMuscle, updatedCalories, updatedInstruction, updatedTags, null);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompleteCommand)) {
            return false;
        }

        // state check
        CompleteCommand e = (CompleteCommand) other;
        return targetIndex.equals(e.targetIndex);
    }
}
