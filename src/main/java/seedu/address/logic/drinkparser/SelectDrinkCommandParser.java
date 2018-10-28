package seedu.address.logic.drinkparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.drinkcommands.SelectDrinkCommand;
import seedu.address.logic.drinkparser.exceptions.DrinkParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectDrinkCommandParser implements DrinkParser<SelectDrinkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws DrinkParseException if the user input does not conform the expected format
     */
    public SelectDrinkCommand parse(String args) throws DrinkParseException {
        try {
            Index index = DrinkParserUtil.parseIndex(args);
            return new SelectDrinkCommand(index);
        } catch (DrinkParseException pe) {
            throw new DrinkParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDrinkCommand.MESSAGE_USAGE), pe);
        }
    }
}
