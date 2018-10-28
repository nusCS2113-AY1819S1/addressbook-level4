package seedu.address.logic.drinkparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.drinkcommands.DeleteDrinkCommand;
import seedu.address.logic.drinkparser.exceptions.DrinkParseException;

/**
 * Parses input arguments and creates a new DeleteDrinkCommand object
 */
public class DeleteDrinkCommandParser implements DrinkParser<DeleteDrinkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws DrinkParseException if the user input does not conform the expected format
     */
    public DeleteDrinkCommand parse(String args) throws DrinkParseException {
        try {
            Index index = DrinkParserUtil.parseIndex(args);
            return new DeleteDrinkCommand(index);
        } catch (DrinkParseException pe) {
            throw new DrinkParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDrinkCommand.MESSAGE_USAGE), pe);
        }
    }
}
