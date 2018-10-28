package seedu.address.logic.drinkparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.drinkcommands.FindDrinkCommand;
import seedu.address.logic.drinkparser.exceptions.DrinkParseException;
import seedu.address.model.drink.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindDrinkCommand object
 */
public class FindDrinkCommandParser implements DrinkParser<FindDrinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws DrinkParseException if the user input does not conform the expected format
     */
    public FindDrinkCommand parse(String args) throws DrinkParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new DrinkParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindDrinkCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
