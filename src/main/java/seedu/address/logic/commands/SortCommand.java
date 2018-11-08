package seedu.address.logic.commands;

import static java.lang.Integer.compare;
import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Parameter;

/**
 * Sorts all persons in the address book by specified criteria.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    // public static final String MESSAGE_ARGUMENTS = "Parameter: %1$s";
    public static final String MESSAGE_SUCCESS = "Sorted as asked";

    private final Parameter parameter;

    public SortCommand(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.getFilteredPersonList().sort(Comparator.comparingInt(o -> o.getSkillLevel().skillLevel));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }
        // state check
        SortCommand e = (SortCommand) other;
        return parameter.equals(e.parameter);
    }
}
