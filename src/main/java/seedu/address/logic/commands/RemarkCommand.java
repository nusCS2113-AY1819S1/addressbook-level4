package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WORKOUTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.workout.Remark;
import seedu.address.model.workout.Workout;


/**
 * Remark a workout in workout book
 */
public class RemarkCommand extends Command {
    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remark the workout identified by the index number used in the displayed workout list.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[" + PREFIX_REMARK + "NAME]\n "
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REMARK
            + "This is a great upper body workout, targeting the muscles in arms, "
            + "the shoulders, the center and your back.";

    public static final String MESSAGE_REMARK_WORKOUT_SUCCESS = "Remark workout: %1$s";
    public static final String MESSAGE_DUPLICATE_WORKOUT = "This workout already exists in the workout book.";

    private final Index index;
    private final Remark remark;

    public RemarkCommand (Index index, Remark remark) {
        requireNonNull(index);

        this.index = index;
        this.remark = remark;
    }

    public Remark getRemark() {
        return remark;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Workout> lastShownList = model.getFilteredWorkoutList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
        }

        Workout workoutToRemark = lastShownList.get(index.getZeroBased());
        Workout remarkedWorkout = createdRemarkedWorkout(workoutToRemark, remark);

        if (!workoutToRemark.isSameWorkout(remarkedWorkout) && model.hasWorkout(remarkedWorkout)) {
            throw new CommandException(MESSAGE_DUPLICATE_WORKOUT);
        }

        model.updateWorkout(workoutToRemark, remarkedWorkout);
        model.updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);
        model.commitModel();
        return new CommandResult(String.format(MESSAGE_REMARK_WORKOUT_SUCCESS, remarkedWorkout));
    }

    /**
     *
     * @param workoutToRemark
     * @param remark
     * @return
     */
    private static Workout createdRemarkedWorkout(Workout workoutToRemark, Remark remark) {

        return new Workout(workoutToRemark.getName(), workoutToRemark.getType(), workoutToRemark.getDuration(),
                workoutToRemark.getDifficulty(), workoutToRemark.getEquipment(),
                workoutToRemark.getMuscle(), workoutToRemark.getCalories(),
                workoutToRemark.getInstruction(), workoutToRemark.getTags(), remark);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
