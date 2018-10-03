//author @tianhang
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_TOPPING;

import java.util.stream.Stream;

import seedu.address.logic.commands.BatchToppingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.Topping;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class BatchToppingCommandParser implements Parser<BatchToppingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BatchToppingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_TOPPING);

        if (!arePrefixesPresent(argMultimap, PREFIX_INGREDIENT_TOPPING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchToppingCommand.MESSAGE_USAGE));
        }
        IngredientName ingredientName = ParserUtil
                .parseIngredientName (argMultimap.getValue (PREFIX_INGREDIENT_TOPPING).get());


        Topping topping = new Topping (ingredientName);

        return new BatchToppingCommand (topping);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
