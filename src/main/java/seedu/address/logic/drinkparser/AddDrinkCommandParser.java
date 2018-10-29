package seedu.address.logic.drinkparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.drinkparser.CliSyntax.PREFIX_DRINK_COST_PRICE;
import static seedu.address.logic.drinkparser.CliSyntax.PREFIX_DRINK_DEFAULT_SELLING_PRICE;
import static seedu.address.logic.drinkparser.CliSyntax.PREFIX_DRINK_NAME;
import static seedu.address.logic.drinkparser.CliSyntax.PREFIX_DRINK_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.drinkcommands.AddDrinkCommand;
import seedu.address.logic.drinkparser.exceptions.DrinkParseException;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Name;
import seedu.address.model.drink.Price;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddDrinkCommand object
 */
public class AddDrinkCommandParser implements DrinkParser<AddDrinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDrinkCommand
     * and returns an AddDrinkCommand object for execution.
     *
     * @throws DrinkParseException if the user input does not conform the expected format
     */
    public AddDrinkCommand parse(String args) throws DrinkParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DRINK_NAME, PREFIX_DRINK_COST_PRICE,
                        PREFIX_DRINK_DEFAULT_SELLING_PRICE, PREFIX_DRINK_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DRINK_NAME, PREFIX_DRINK_COST_PRICE,
                PREFIX_DRINK_DEFAULT_SELLING_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new DrinkParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDrinkCommand.MESSAGE_USAGE));
        }

        Name drinkName = DrinkParserUtil.parseDrinkName(argMultimap.getValue(PREFIX_DRINK_NAME).get());
        Price costPrice = DrinkParserUtil.parseDrinkCostPrice(argMultimap.getValue(PREFIX_DRINK_COST_PRICE).get());
        Price defaultSellingPrice = DrinkParserUtil
                .parseDrinkDefaultSellingPrice(argMultimap.getValue(PREFIX_DRINK_DEFAULT_SELLING_PRICE).get());
        Set<Tag> tagList = DrinkParserUtil.parseTags(argMultimap.getAllValues(PREFIX_DRINK_TAG));

        Drink drink = new Drink(drinkName, costPrice, defaultSellingPrice, tagList);

        return new AddDrinkCommand(drink);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
