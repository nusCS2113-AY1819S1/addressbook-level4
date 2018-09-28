package t13g2.forum.logic.commands;

import static java.util.Objects.requireNonNull;

import t13g2.forum.logic.CommandHistory;
import t13g2.forum.model.AddressBook;
import t13g2.forum.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new AddressBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
