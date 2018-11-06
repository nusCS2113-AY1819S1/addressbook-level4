package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventContainsKeywordsPredicate;

/**
 * Finds and lists all events in event manager which the user has registered for.
 */
public class AttendingCommand extends Command {

    public static final String COMMAND_WORD = "attending";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events you have registered for "
            + "and displays them as a list with index numbers.\n";
    public static final String MESSAGE_SUCCESS = "Listed all attending events\n";

    private final EventContainsKeywordsPredicate predicate;

    public AttendingCommand(EventContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.getLoginStatus()) {
            throw new CommandException(MESSAGE_LOGIN);
        }

        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AttendingCommand // instanceof handles nulls
                && predicate.equals(((AttendingCommand) other).predicate)); // state check
    }
}
