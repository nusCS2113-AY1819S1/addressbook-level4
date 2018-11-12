package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * The {@code DeleteScheduleCommand} class is used for deleting a schedule identified using it's
 * displayed index from the schedule list observable panel list.
 */
public class DeleteScheduleCommand extends Command {
    public static final String COMMAND_WORD = "deleteSchedule";
    public static final String COMMAND_ALIAS = "ds";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the schedule identified by the index number used in the displayed schedule list."
            + "\nParameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SCHEDULE_SUCCESS = "Deleted Schedule: %1$s";

    private final Index targetIndex;

    public DeleteScheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * CommandResult execution.
     * @param model {@code Model} which the command will operate on the model.
     * @param history {@code CommandHistory} which the command history will be added.
     * @return CommandResult, String success feedback to the user.
     * @throws CommandException  String failure feedback to the user if error in execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Schedule> lastShownList = model.getFilteredScheduleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }

        Schedule scheduleToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteSchedule(scheduleToDelete);
        model.commitScheduleList();
        return new CommandResult(String.format(MESSAGE_DELETE_SCHEDULE_SUCCESS, scheduleToDelete));
    }

    /**
     * Compares if both objects are equal.
     * @param other similar object type to be compared with.
     * @return Boolean, True if both objects are equal based on the defined conditions.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteScheduleCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteScheduleCommand) other).targetIndex)); // state check
    }
}
