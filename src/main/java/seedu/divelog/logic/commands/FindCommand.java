package seedu.divelog.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.logic.CommandHistory;
import seedu.divelog.model.Model;
import seedu.divelog.model.dive.LocationContainsKeywordPredicate;

/**
 * Finds and lists all dive sessions in divelog book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds a dive based on location";

    private final LocationContainsKeywordPredicate predicate;

    public FindCommand(LocationContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDiveList(predicate);
        if (model.getPlanningMode()) {
            model.plannerCountPlus();
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_DIVE_LISTED_OVERVIEW, model.getFilteredDiveList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
