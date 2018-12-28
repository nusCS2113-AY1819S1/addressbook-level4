package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

//@@author chokxy
/**
 * Sorts and lists all persons in address book by their name.
 * (Currently can only sort by PREFIX name and ORDER 1)
 */

public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons by specified prefix in either "
            + "lexicographical or reverse order and display as a list with index numbers.\n"
            + "An empty NRIC field will leave patients at the top of the list while sorting by alphabetical order.\n"
            + "Parameters:  PREFIX/ ORDER_INDEX \n"
            + "Example: " + COMMAND_WORD + " p/ 2 \n"
            + "Available prefixes:\t n/ (Name)\t p/ (Phone)\t e/ (Email)\t\t ic/ (Nric)\n"
            + "Order Indexes:\t\t 1 (Alphabetical)\t\t 2 (Reverse)";

    private final String prefix;
    private final int order;

    public SortCommand(String prefix, int order) {
        this.prefix = prefix;
        this.order = order;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateSortedPersonList(prefix, order);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_SORTED, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && prefix.equals(((SortCommand) other).prefix));
    }
}
