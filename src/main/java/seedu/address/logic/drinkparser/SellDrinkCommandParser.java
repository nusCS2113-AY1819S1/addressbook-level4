package seedu.address.logic.drinkparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.drinkparser.CliSyntax.PREFIX_DRINK_NAME;
import static seedu.address.logic.drinkparser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.logic.drinkcommands.SellDrinkCommand;
import seedu.address.logic.drinkparser.exceptions.DrinkParseException;
import seedu.address.model.drink.Name;
import seedu.address.model.drink.Quantity;

/**
 * Parses input arguments and creates a new SellDrinkCommand object
 */
public class SellDrinkCommandParser implements DrinkParser<SellDrinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws DrinkParseException if the user input does not conform the expected format
     */
    public SellDrinkCommand parse(String args) throws DrinkParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DRINK_NAME, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DRINK_NAME, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new DrinkParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SellDrinkCommand.MESSAGE_USAGE));
        }

        Name drinkName = DrinkParserUtil.parseDrinkName(argMultimap.getValue(PREFIX_DRINK_NAME).get());
        Quantity quantitySold = DrinkParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());

        return new SellDrinkCommand(drinkName, quantitySold);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
