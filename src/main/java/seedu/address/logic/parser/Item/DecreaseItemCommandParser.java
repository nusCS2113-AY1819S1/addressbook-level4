package seedu.address.logic.parser.Item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_QUANTITY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ItemCommand.DecreaseItemCommand;
import seedu.address.logic.commands.ItemCommand.IncreaseItemCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.ItemQuantity;

/**
 * Parses input arguments and creates a new DecreaseItemCommand object
 */
public class DecreaseItemCommandParser implements Parser<DecreaseItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DecreaseItemCommand
     * and returns an DecreaseItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DecreaseItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ITEM_QUANTITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DecreaseItemCommand.MESSAGE_USAGE),
                pe);
        }

        if (!arePrefixesPresent(argumentMultimap, PREFIX_ITEM_QUANTITY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncreaseItemCommand.MESSAGE_USAGE));
        }

        ItemQuantity itemQuantity = ParserUtil.parseItemQuantity(argumentMultimap.getValue(PREFIX_ITEM_QUANTITY).get());

        return new DecreaseItemCommand(index, itemQuantity);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
