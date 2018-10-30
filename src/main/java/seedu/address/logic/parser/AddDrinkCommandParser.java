package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_COST_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_DEFAULT_SELLING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddDrinkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.Name;
import seedu.address.model.drink.Price;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddDrinkCommand object
 */
public class AddDrinkCommandParser implements Parser<AddDrinkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDrinkCommand
     * and returns an AddDrinkCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddDrinkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DRINK_NAME, PREFIX_DRINK_COST_PRICE,
                        PREFIX_DRINK_DEFAULT_SELLING_PRICE, PREFIX_DRINK_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DRINK_NAME, PREFIX_DRINK_COST_PRICE,
                PREFIX_DRINK_DEFAULT_SELLING_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDrinkCommand.MESSAGE_USAGE));
        }

        Name drinkName = ParserUtil.parseDrinkName(argMultimap.getValue(PREFIX_DRINK_NAME).get());
        Price costPrice = ParserUtil.parseDrinkCostPrice(argMultimap.getValue(PREFIX_DRINK_COST_PRICE).get());
        Price defaultSellingPrice = ParserUtil
                .parseDrinkDefaultSellingPrice(argMultimap.getValue(PREFIX_DRINK_DEFAULT_SELLING_PRICE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_DRINK_TAG));

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
