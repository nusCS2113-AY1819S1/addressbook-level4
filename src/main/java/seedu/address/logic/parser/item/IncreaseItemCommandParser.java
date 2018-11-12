package seedu.address.logic.parser.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_QUANTITY;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.item.IncreaseItemCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.ItemQuantity;

/**
 * Parses input arguments and creates a new IncreaseItemCommand object
 */
public class IncreaseItemCommandParser implements Parser<IncreaseItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the IncreaseItemCommand
     * and returns an IncreaseItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public IncreaseItemCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ITEM_QUANTITY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncreaseItemCommand.MESSAGE_USAGE),
                    pe);
        }

        if (!arePrefixesPresent(argumentMultimap, PREFIX_ITEM_QUANTITY)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, IncreaseItemCommand.MESSAGE_USAGE));
        }

        ItemQuantity itemQuantity = ParserUtil.parseItemQuantity(argumentMultimap.getValue(PREFIX_ITEM_QUANTITY).get());

        return new IncreaseItemCommand(index, itemQuantity);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
