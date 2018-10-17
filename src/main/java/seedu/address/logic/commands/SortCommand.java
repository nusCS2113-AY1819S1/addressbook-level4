package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Sorts and lists all persons in address book by their name.
 * (Currently can only sort by PREFIX name and ORDER 1)
 */

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " all persons by PREFIX by preferred order "
            + "and display as a list with index numbers.\n"
            + "Parameters:  PREFIX INDEX (1 for alphabetical, 2 for reverse order) \n"
            + "Example: " + COMMAND_WORD + " n/ " + "1";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateSortedPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_SORTED, model.getSortedPersonList().size()));
    }
}
