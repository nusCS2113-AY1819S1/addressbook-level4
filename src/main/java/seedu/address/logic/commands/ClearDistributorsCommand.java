package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DistributorBook;
import seedu.address.model.Model;


/**
 * Clears the address book.
 */
public class ClearDistributorsCommand extends Command {

    public static final String COMMAND_WORD = "cleardistributors";
    public static final String MESSAGE_SUCCESS = "Distributor book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new DistributorBook());
        model.commitDistributorBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
