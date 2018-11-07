package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ProductDatabase;


/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clearproducts";
    public static final String MESSAGE_SUCCESS = "ProductInfo book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new ProductDatabase());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
