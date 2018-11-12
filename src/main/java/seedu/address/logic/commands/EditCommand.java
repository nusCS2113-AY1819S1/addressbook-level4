package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EQUIPMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WORKOUTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
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
 * Edits the details of an existing workout in the workout book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the workout identified "
            + "by the index number used in the displayed workout list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TYPE + "TYPE] "
            + "[" + PREFIX_DURATION + "DURATION] "
            + "[" + PREFIX_DIFFICULTY + "DIFFICULTY] "
            + "[" + PREFIX_EQUIPMENT + "EQUIPMENT] "
            + "[" + PREFIX_MUSCLE + "MUSCLE] "
            + "[" + PREFIX_CALORIES + "CALORIES] "
            + "[" + PREFIX_INSTRUCTION + "INSTRUCTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TYPE + "Anaerobic "
            + PREFIX_DIFFICULTY + "beginner";

    public static final String MESSAGE_EDIT_WORKOUT_SUCCESS = "Edited Workout: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_WORKOUT = "This workout already exists in the workout book.";
    public static final String MESSAGE_EDIT_BLANK_WORKOUT = "Do not use \"tag/\" anymore when there is only the state"
        + "tag remaining.";

    private final Index index;
    private final EditWorkoutDescriptor editWorkoutDescriptor;

    /**
     * @param index of the workout in the filtered workout list to edit
     * @param editWorkoutDescriptor details to edit the workout with
     */
    public EditCommand(Index index, EditWorkoutDescriptor editWorkoutDescriptor) {
        requireNonNull(index);
        requireNonNull(editWorkoutDescriptor);

        this.index = index;
        this.editWorkoutDescriptor = new EditWorkoutDescriptor(editWorkoutDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Workout> lastShownList = model.getFilteredWorkoutList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
        }

        Workout workoutToEdit = lastShownList.get(index.getZeroBased());
        Workout editedWorkout = createEditedWorkout(workoutToEdit, editWorkoutDescriptor);

        if (!workoutToEdit.isSameWorkout(editedWorkout) && model.hasWorkout(editedWorkout)) {
            throw new CommandException(MESSAGE_DUPLICATE_WORKOUT);
        }

        model.updateWorkout(workoutToEdit, editedWorkout);
        model.updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);
        model.commitModel();
        return new CommandResult(String.format(MESSAGE_EDIT_WORKOUT_SUCCESS, editedWorkout));
    }

    /**
     * Creates and returns a {@code Workout} with the details of {@code workoutToEdit}
     * edited with {@code editWorkoutDescriptor}.
     */
    private static Workout createEditedWorkout(Workout workoutToEdit, EditWorkoutDescriptor editWorkoutDescriptor)
            throws CommandException {
        assert workoutToEdit != null;

        Name updatedName = editWorkoutDescriptor.getName().orElse(workoutToEdit.getName());
        Type updatedType = editWorkoutDescriptor.getType().orElse(workoutToEdit.getType());
        Duration updatedDuration = editWorkoutDescriptor.getDuration().orElse(workoutToEdit.getDuration());
        Difficulty updatedDifficulty = editWorkoutDescriptor.getDifficulty().orElse(workoutToEdit.getDifficulty());
        Equipment updatedEquipment = editWorkoutDescriptor.getEquipment().orElse(workoutToEdit.getEquipment());
        Muscle updatedMuscle = editWorkoutDescriptor.getMuscle().orElse(workoutToEdit.getMuscle());
        Calories updatedCalories = editWorkoutDescriptor.getCalories().orElse(workoutToEdit.getCalories());
        Instruction updatedInstruction = editWorkoutDescriptor.getInstruction().orElse(workoutToEdit.getInstruction());

        Tag stateTag = null;
        boolean otherTags = false;
        for (Tag entry: workoutToEdit.getTags()) {
            if (entry.tagName.equals("future")) {
                stateTag = entry;
            } else if (entry.tagName.equals("current")) {
                stateTag = entry;
            } else if (entry.tagName.equals("completed")) {
                stateTag = entry;
            } else {
                otherTags = true;
            }
        }
        Set<Tag> originalTags = editWorkoutDescriptor.getTags().orElse(workoutToEdit.getTags());
        if (originalTags.isEmpty() && !otherTags) {
            throw new CommandException(MESSAGE_EDIT_BLANK_WORKOUT);
        }
        Set<Tag> updatedTags = new HashSet<>();
        for (Tag entry: originalTags) {
            if (entry.tagName.equals("future") || entry.tagName.equals("current")
                    || entry.tagName.equals("completed")) {
            } else {
                updatedTags.add(entry);
            }
        }
        if (stateTag != null) {
            updatedTags.add(stateTag);
        }

        return new Workout(updatedName, updatedType, updatedDuration, updatedDifficulty, updatedEquipment,
                updatedMuscle, updatedCalories, updatedInstruction, updatedTags, workoutToEdit.getRemark());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editWorkoutDescriptor.equals(e.editWorkoutDescriptor);
    }

    /**
     * Stores the details to edit the workout with. Each non-empty field value will replace the
     * corresponding field value of the workout.
     */
    public static class EditWorkoutDescriptor {
        private Name name;
        private Type type;
        private Duration duration;
        private Difficulty difficulty;
        private Equipment equipment;
        private Muscle muscle;
        private Calories calories;
        private Instruction instruction;
        private Set<Tag> tags;

        public EditWorkoutDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditWorkoutDescriptor(EditWorkoutDescriptor toCopy) {
            setName(toCopy.name);
            setType(toCopy.type);
            setDuration(toCopy.duration);
            setDifficulty(toCopy.difficulty);
            setEquipment(toCopy.equipment);
            setMuscle(toCopy.muscle);
            setCalories(toCopy.calories);
            setInstruction(toCopy.instruction);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, type, duration, difficulty, equipment, muscle, calories,
                    instruction, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setType(Type type) {
            this.type = type;
        }

        public Optional<Type> getType() {
            return Optional.ofNullable(type);
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
        }

        public void setDifficulty(Difficulty difficulty) {
            this.difficulty = difficulty;
        }

        public Optional<Difficulty> getDifficulty() {
            return Optional.ofNullable(difficulty);
        }

        public void setEquipment(Equipment equipment) {
            this.equipment = equipment;
        }

        public Optional<Equipment> getEquipment() {
            return Optional.ofNullable(equipment);
        }

        public void setMuscle(Muscle muscle) {
            this.muscle = muscle;
        }

        public Optional<Muscle> getMuscle() {
            return Optional.ofNullable(muscle);
        }

        public void setCalories(Calories calories) {
            this.calories = calories;
        }

        public Optional<Calories> getCalories() {
            return Optional.ofNullable(calories);
        }

        public void setInstruction(Instruction instruction) {
            this.instruction = instruction;
        }

        public Optional<Instruction> getInstruction() {
            return Optional.ofNullable(instruction);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditWorkoutDescriptor)) {
                return false;
            }

            // state check
            EditWorkoutDescriptor e = (EditWorkoutDescriptor) other;

            return getName().equals(e.getName())
                    && getType().equals(e.getType())
                    && getDuration().equals(e.getDuration())
                    && getDifficulty().equals(e.getDifficulty())
                    && getEquipment().equals(e.getEquipment())
                    && getMuscle().equals(e.getMuscle())
                    && getCalories().equals(e.getCalories())
                    && getInstruction().equals(e.getInstruction())
                    && getTags().equals(e.getTags());
        }
    }
}
