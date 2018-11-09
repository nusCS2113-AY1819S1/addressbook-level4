package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.event.EventContainsKeywordsPredicate;
/**
 * Finds and lists all events in event whose name and description contains any of the argument keywords
 * Keyword matching is case insensitive.
 */
public class FindEventCommand extends Command {

    public static final String COMMAND_WORD = "findEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose names or description contain "
            + "any of the specified keywords (case-insensitive) "
            + "or the description matches the keywords (case-insensitive) "
            + "and displays them.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " meeting";

    private final EventContainsKeywordsPredicate predicate;

    public FindEventCommand(EventContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindEventCommand // instanceof handles nulls
                && predicate.equals(((FindEventCommand) other).predicate)); // state check
    }
}
