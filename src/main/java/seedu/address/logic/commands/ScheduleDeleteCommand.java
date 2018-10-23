package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.schedule.Activity;

public class ScheduleDeleteCommand extends ScheduleCommand {
    private static final String MESSAGE_SUCCESS = "Task \"%s\" on %s has been deleted.";
    private final Index index;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public ScheduleDeleteCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult updateSchedule(Model model) throws CommandException {
        Activity toDelete = getActivityFromIndex(model, index);
        model.deleteActivity(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                toDelete.getActivity(),
                Activity.getDateString(toDelete.getDate())));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleDeleteCommand// instanceof handles nulls
                && index.equals(((ScheduleDeleteCommand) other).index));
    }
}
