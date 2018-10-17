package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command implements CommandParser {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetData(new AddressBook());
        model.commitTaskBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new ClearCommand();
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
