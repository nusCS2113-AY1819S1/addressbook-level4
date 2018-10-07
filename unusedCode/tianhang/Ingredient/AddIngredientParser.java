//author @tianhang
package seedu.address.logic.parser.Ingredient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_ICECREAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_TOPPING;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.logic.commands.Ingredient.IngredientCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse command under batch
 */
public class AddIngredientParser {
    public static final Pattern ICE_CREAM_CHECKER = Pattern.compile ("(?<iceCream>i/)");
    public static final Pattern TOPPING_CHECKER = Pattern.compile ("(?<topping>t/)");

    /**
     * Split command into individual parsers
     * @param args Input string of all item e.g i/ice
     * @return the result of the command execution
     * @throws ParseException If an error occurs during parsing.
     */
    public IngredientCommand split (String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_ICECREAM, PREFIX_INGREDIENT_TOPPING);

        if ((!arePrefixesPresent(argMultimap, PREFIX_INGREDIENT_ICECREAM)
                && !arePrefixesPresent(argMultimap, PREFIX_INGREDIENT_TOPPING))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IngredientCommand.MESSAGE_USAGE_ADD_INGREDIENT));
        }
        Matcher m = ICE_CREAM_CHECKER.matcher (args);
        System.out.println (m);
        if (m.find ()) {
            System.out.println (1);
            return new AddIceCreamCommandParser ().parse (args);
        }
        m = TOPPING_CHECKER.matcher (args);
        System.out.println (m);
        if (m.find ()) {
            System.out.println (2);
            return new AddToppingCommandParser ().parse (args);
        }

        return new IngredientCommand ();
    }
    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
