//author @tianhang
package seedu.address.logic.parser.Ingredient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_TOPPING;

import java.util.stream.Stream;

import seedu.address.logic.commands.Ingredient.AddToppingCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.Topping;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddToppingCommandParser implements Parser< AddToppingCommand > {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddToppingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_TOPPING);

        if (!arePrefixesPresent(argMultimap, PREFIX_INGREDIENT_TOPPING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToppingCommand.MESSAGE_USAGE_ADD_INGREDIENT));
        }
        IngredientName ingredientName = ParserUtil
                .parseIngredientName (argMultimap.getValue (PREFIX_INGREDIENT_TOPPING).get());


        Topping topping = new Topping (ingredientName);

        return new AddToppingCommand (topping);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
