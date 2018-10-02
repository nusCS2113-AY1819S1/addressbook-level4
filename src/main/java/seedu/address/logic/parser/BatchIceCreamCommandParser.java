//author @tianhang
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_ICECREAM;

import java.util.stream.Stream;

import seedu.address.logic.commands.BatchIceCreamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IceCream;
import seedu.address.model.ingredient.IngredientName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class BatchIceCreamCommandParser implements Parser<BatchIceCreamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BatchIceCreamCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_ICECREAM);

        if (!arePrefixesPresent(argMultimap, PREFIX_INGREDIENT_ICECREAM)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchIceCreamCommand.MESSAGE_USAGE));
        }
        IngredientName ingredientName = ParserUtil
                .parseIngredientName (argMultimap.getValue (PREFIX_INGREDIENT_ICECREAM).get());

        IceCream iceCream = new IceCream (ingredientName);

        return new BatchIceCreamCommand (iceCream);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
