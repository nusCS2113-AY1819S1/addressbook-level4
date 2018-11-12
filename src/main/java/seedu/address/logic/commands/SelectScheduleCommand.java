package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListScheduleRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * The {@code SelectScheduleCommand} class is used selecting a schedule identified using
 * it's displayed index from the schedule list panel.
 */
public class SelectScheduleCommand extends Command {
    public static final String COMMAND_WORD = "selectSchedule";
    public static final String COMMAND_ALIAS = "ss";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the Schedule identified by the index number used in the displayed schedule list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_SCHEDULE_SUCCESS = "Selected Schedule: %1$s";

    private final Index targetIndex;

    public SelectScheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * SelectScheduleCommand execution.
     * <p>
     *     Selects a schedule identified using it's displayed index from the schedule list panel
     * </p>
     * @param model {@code Model} which the command will operate on the model.
     * @param history {@code CommandHistory} which the command history will be added.
     * @return CommandResult, String success feedback to the user.
     * @throws CommandException  String failure feedback to the user if error in execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Schedule> filteredScheduleList = model.getFilteredScheduleList();

        if (targetIndex.getZeroBased() >= filteredScheduleList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListScheduleRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_SCHEDULE_SUCCESS, targetIndex.getOneBased()));

    }

    /**
     * Compares if both objects are equal.
     * @param other similar object type to be compared with.
     * @return Boolean, True if both objects are equal based on the defined conditions.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectScheduleCommand // instanceof handles nulls
                && targetIndex.equals(((SelectScheduleCommand) other).targetIndex)); // state check
    }
}
