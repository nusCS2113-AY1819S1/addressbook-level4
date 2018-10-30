package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.logic.commands.ImportDrinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Name;
import seedu.address.model.drink.Quantity;


/**
 * Parses input arguments and creates a new ImportDrinkCommand object
 */
public class ImportDrinkCommandParser implements Parser<ImportDrinkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ImportDrinkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DRINK_NAME, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_DRINK_NAME, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ImportDrinkCommand.MESSAGE_USAGE));
        }

        Name drinkName = ParserUtil.parseDrinkName(argMultimap.getValue(PREFIX_DRINK_NAME).get());
        Drink drink = new Drink(drinkName);
        Quantity quantitySold = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());

        return new ImportDrinkCommand(drink, quantitySold);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
