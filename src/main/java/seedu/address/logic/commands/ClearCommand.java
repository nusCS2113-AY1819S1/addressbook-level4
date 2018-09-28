package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.FileEncryptor;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = CliSyntax.COMMAND_CLEAR;
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        FileEncryptor fe = new FileEncryptor("data/addressbook.xml");

        if (fe.isLocked()) {
            throw new CommandException(fe.MESSAGE_ADDRESS_BOOK_LOCKED);
        }

        model.resetData(new AddressBook());
        model.commitAddressBook();
        model.clearAllTries();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
